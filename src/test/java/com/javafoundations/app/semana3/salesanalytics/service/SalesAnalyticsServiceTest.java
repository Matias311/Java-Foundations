package com.javafoundations.app.semana3.salesanalytics.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.javafoundations.app.semana3.salesanalytics.model.CategoryRevenue;
import com.javafoundations.app.semana3.salesanalytics.model.ProductQuantity;
import com.javafoundations.app.semana3.salesanalytics.model.ProductRevenue;
import com.javafoundations.app.semana3.salesanalytics.model.SaleRecord;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

@DisplayName("Test for the service sales analytics")
public class SalesAnalyticsServiceTest {
  private SalesAnalyticsService service;
  private ArrayList<SaleRecord> sales;

  @BeforeEach
  void setUp() {
    this.service = new SalesAnalyticsService();
    this.sales =
        new ArrayList<>(
            List.of(
                SaleRecord.create(
                    "11",
                    LocalDateTime.parse("2026-01-01T10:15:00"),
                    "2345",
                    "Mouse",
                    "Hardware",
                    10,
                    new BigDecimal("19.99")),
                SaleRecord.create(
                    "22",
                    LocalDateTime.parse("2026-01-01T12:40:00"),
                    "334",
                    "Teclado",
                    "Hardware",
                    1,
                    new BigDecimal("10.99")),
                SaleRecord.create(
                    "23",
                    LocalDateTime.parse("2026-01-01T12:40:00"),
                    "335",
                    "CPU",
                    "Hardware",
                    1,
                    new BigDecimal("10.99"))));
  }

  @Test
  @DisplayName("Calculate the revenue by category")
  void calculateRevenueByCategory() {
    Map<String, BigDecimal> revenueByCategory = service.revenueByCategory(sales);
    assertEquals(0, revenueByCategory.get("Hardware").compareTo(new BigDecimal("221.88")));
  }

  @Test
  @DisplayName("Top 1 product by Revenue")
  void topProductByRevenue() {
    List<ProductRevenue> list = service.topProductByRevenue(sales, 1);
    assertAll(
        "Assert for the data in the product revenue",
        () -> assertEquals("Mouse", list.get(0).productName(), () -> "Product name incorrect"),
        () ->
            assertEquals(
                new BigDecimal("199.90"), list.get(0).revenue(), () -> "Revenue incorrect"));
  }

  @Test
  @DisplayName("Top 0 product by Revenue")
  void topZeroProductByRevenue() {
    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> service.topProductByRevenue(sales, 0));
    assertEquals("The topN value can not be zero >= 0", exception.getMessage());
  }

  @Test
  @DisplayName("Top -20 product by Revenue")
  void topMinusTwentyProductByRevenue() {
    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> service.topProductByRevenue(sales, -20));
    assertEquals("The topN value can not be zero >= 0", exception.getMessage());
  }

  @Test
  @DisplayName("The top N is more than the list size")
  void topNmoreThanTheListSize() {
    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> service.topProductByRevenue(sales, 20));
    assertEquals("The topN value can not be more than the sales list size", exception.getMessage());
  }

  @Test
  @DisplayName("The top N (tie-breaker)")
  void topNtieBreaker() {
    sales.add(
        SaleRecord.create(
            "11",
            LocalDateTime.parse("2026-01-02T10:15:00"),
            "2345",
            "Teclado",
            "Hardware",
            10,
            new BigDecimal("19.99")));
    List<ProductRevenue> top = service.topProductByRevenue(sales, 2);
    assertAll(
        "Test for the list",
        () ->
            assertTrue(
                top.get(0).productName().equals("Teclado"),
                () -> "Error: The first element was not the teclado"),
        () ->
            assertTrue(
                top.get(1).productName().equals("Mouse"),
                () -> "Error: The second element its not a mouse"),
        () -> assertTrue(top.size() == 2, () -> "Error: the size must be two"));
  }

  @Test
  @DisplayName("Revenue by the day with one day")
  void revenueByTheDayWithOneDay() {
    Map<LocalDate, BigDecimal> revenueDay = service.revenueByDay(sales);
    assertAll(
        "assert for the date and revenue",
        () -> assertTrue(revenueDay.keySet().contains(LocalDate.parse("2026-01-01"))),
        () ->
            assertEquals(new BigDecimal("221.88"), revenueDay.get(LocalDate.parse("2026-01-01"))));
  }

  @Test
  @DisplayName("Revenue by the day with 3 days")
  void revenueByTheDayWithThreeDays() {
    sales.add(
        SaleRecord.create(
            "11",
            LocalDateTime.parse("2026-01-02T10:15:00"),
            "2345",
            "Mouse",
            "Hardware",
            1,
            new BigDecimal("19.99")));

    sales.add(
        SaleRecord.create(
            "11",
            LocalDateTime.parse("2026-01-03T10:15:00"),
            "2345",
            "Mouse",
            "Hardware",
            1,
            new BigDecimal("19.99")));

    Map<LocalDate, BigDecimal> revenueDay = service.revenueByDay(sales);
    assertAll(
        "assert for the date and revenue",
        () -> assertTrue(revenueDay.containsKey(LocalDate.parse("2026-01-01"))),
        () -> assertEquals(new BigDecimal("221.88"), revenueDay.get(LocalDate.parse("2026-01-01"))),
        () -> assertTrue(revenueDay.containsKey(LocalDate.parse("2026-01-02"))),
        () -> assertEquals(new BigDecimal("19.99"), revenueDay.get(LocalDate.parse("2026-01-02"))),
        () -> assertTrue(revenueDay.containsKey(LocalDate.parse("2026-01-03"))),
        () -> assertEquals(new BigDecimal("19.99"), revenueDay.get(LocalDate.parse("2026-01-03"))));
  }

  @Test
  @DisplayName("Revenue by Month")
  void revenueByMonth() {
    Map<YearMonth, BigDecimal> revenue = service.revenueByMonth(sales);
    assertAll(
        "Test for the date 2026-01",
        () -> assertTrue(revenue.containsKey(YearMonth.of(2026, 01))),
        () -> assertEquals(new BigDecimal("221.88"), revenue.get(YearMonth.of(2026, 01))));
  }

  @Test
  @DisplayName("Set of duplicate sale ids")
  void setDuplicateSaleIds() {
    sales.add(
        SaleRecord.create(
            "11",
            LocalDateTime.parse("2026-01-02T10:15:00"),
            "2345",
            "Mouse",
            "Hardware",
            1,
            new BigDecimal("19.99")));

    sales.add(
        SaleRecord.create(
            "23",
            LocalDateTime.parse("2026-01-03T10:15:00"),
            "2345",
            "Mouse",
            "Hardware",
            1,
            new BigDecimal("19.99")));
    Set<String> duplicate = service.duplicateSaleIds(sales);
    assertAll(
        "Checks the duplicate values",
        () -> assertTrue(duplicate.contains("11")),
        () -> assertTrue(duplicate.contains("23")),
        () -> assertTrue(duplicate.size() == 2, () -> "Error, the lenght doesn't match"));
  }

  @Test
  @DisplayName("Find the revenue outliners using 50.00")
  void findRevenueOutliners() {
    List<SaleRecord> revenueOutliners =
        service.findRevenueOutliners(sales, new BigDecimal("50.00"));
    assertTrue(revenueOutliners.get(0).revenue().compareTo(new BigDecimal("50.00")) > 0);
  }

  @Test
  @DisplayName("Find the revenue outliners using -10.44")
  void findRevenueOutlinersUsingNegativeValue() {
    Exception ex =
        assertThrows(
            IllegalArgumentException.class,
            () -> service.findRevenueOutliners(sales, new BigDecimal("-10.44")));
    assertEquals("The threshold must be >= 0", ex.getMessage());
  }

  @Test
  @DisplayName("Obtein the revenue by the productId")
  void getRevenueProductId() {
    Map<String, BigDecimal> revenueProductId = service.revenueByProductId(sales);
    assertAll(
        "Obtain the value with the key and verify",
        () -> assertEquals(new BigDecimal("199.90"), revenueProductId.get("2345")),
        () -> assertEquals(new BigDecimal("10.99"), revenueProductId.get("334")),
        () -> assertEquals(new BigDecimal("10.99"), revenueProductId.get("335")),
        () -> assertTrue(revenueProductId.size() == 3, "Error: the size was incorrect"));
  }

  @Test
  @DisplayName("Total Quantity by category")
  void queantityByCategory() {
    sales.add(
        SaleRecord.create(
            "11",
            LocalDateTime.parse("2026-01-02T10:15:00"),
            "2345",
            "Word",
            "Software",
            3,
            new BigDecimal("20.99")));
    Map<String, Integer> quantityCategory = service.quantityByCategory(sales);
    assertAll(
        "Obtain the total quantity using the category and verify the quantity",
        () -> assertEquals(12, quantityCategory.get("Hardware")),
        () -> assertEquals(3, quantityCategory.get("Software")),
        () -> assertTrue(quantityCategory.size() == 2));
  }

  @Test
  @DisplayName("Top 2 categories by revenue")
  void topTwoCateforiesByRevenue() {
    sales.add(
        SaleRecord.create(
            "11",
            LocalDateTime.parse("2026-01-02T10:15:00"),
            "2345",
            "Word",
            "Software",
            3,
            new BigDecimal("20.99")));

    sales.add(
        SaleRecord.create(
            "11",
            LocalDateTime.parse("2026-01-02T10:15:00"),
            "2345",
            "Pc pre armado",
            "Pc",
            1,
            new BigDecimal("10.99")));
    List<CategoryRevenue> categoryRevenueList = service.topCategoriesByRevenue(sales, 2);
    assertAll(
        "Obtain the Category Revenue and verify the value",
        () -> assertEquals("Hardware", categoryRevenueList.get(0).category()),
        () -> assertEquals("Software", categoryRevenueList.get(1).category()),
        () -> assertTrue(categoryRevenueList.size() == 2));
  }

  @Test
  @DisplayName("Top category by -2(throw illegalargument)")
  void topCategoryMinusTwo() {
    sales.add(
        SaleRecord.create(
            "11",
            LocalDateTime.parse("2026-01-02T10:15:00"),
            "2345",
            "Word",
            "Software",
            3,
            new BigDecimal("20.99")));

    sales.add(
        SaleRecord.create(
            "11",
            LocalDateTime.parse("2026-01-02T10:15:00"),
            "2345",
            "Pc pre armado",
            "Pc",
            1,
            new BigDecimal("10.99")));
    Exception ex =
        assertThrows(
            IllegalArgumentException.class, () -> service.topCategoriesByRevenue(sales, -2));
    assertEquals("Top N must be > 0 and must be < sales.size", ex.getMessage());
  }

  @Test
  @DisplayName("Top category by 0 (throw illegalargument)")
  void topCategoryZero() {
    sales.add(
        SaleRecord.create(
            "11",
            LocalDateTime.parse("2026-01-02T10:15:00"),
            "2345",
            "Word",
            "Software",
            3,
            new BigDecimal("20.99")));

    sales.add(
        SaleRecord.create(
            "11",
            LocalDateTime.parse("2026-01-02T10:15:00"),
            "2345",
            "Pc pre armado",
            "Pc",
            1,
            new BigDecimal("10.99")));
    Exception ex =
        assertThrows(
            IllegalArgumentException.class, () -> service.topCategoriesByRevenue(sales, 0));
    assertEquals("Top N must be > 0 and must be < sales.size", ex.getMessage());
  }

  @Test
  @DisplayName("Top category by 20 (throw illegalargument)")
  void topCategoryTwenty() {
    sales.add(
        SaleRecord.create(
            "11",
            LocalDateTime.parse("2026-01-02T10:15:00"),
            "2345",
            "Word",
            "Software",
            3,
            new BigDecimal("20.99")));

    sales.add(
        SaleRecord.create(
            "11",
            LocalDateTime.parse("2026-01-02T10:15:00"),
            "2345",
            "Pc pre armado",
            "Pc",
            1,
            new BigDecimal("10.99")));
    Exception ex =
        assertThrows(
            IllegalArgumentException.class, () -> service.topCategoriesByRevenue(sales, 20));
    assertEquals("Top N must be > 0 and must be < sales.size", ex.getMessage());
  }

  @Test
  @DisplayName("Revenue by the hour")
  void revenueByTheHour() {
    sales.add(
        SaleRecord.create(
            "11",
            LocalDateTime.parse("2026-01-02T10:15:00"),
            "2345",
            "Teclado",
            "Hardware",
            10,
            new BigDecimal("19.99")));

    Map<Integer, BigDecimal> revenue = service.revenueByHour(sales);
    assertEquals(new BigDecimal("399.80"), revenue.get(10));
  }

  @Test
  @DisplayName("List of unique product by category")
  void uniqueProductByCategory() {
    Map<String, Set<String>> producNameCategory = service.productNamesByCategory(sales);
    assertAll(
        "Test for the productNameCategory map. size, test of values",
        () -> assertTrue(producNameCategory.get("Hardware").contains("Mouse")),
        () -> assertTrue(producNameCategory.get("Hardware").contains("CPU")),
        () -> assertTrue(producNameCategory.get("Hardware").contains("Teclado")),
        () -> assertEquals(3, producNameCategory.get("Hardware").size()),
        () -> assertEquals(1, producNameCategory.size()));
  }

  @Test
  @DisplayName("List of unique product by category with more than one category")
  void uniqueProductByCategoryWithMoreThanOneCategory() {
    sales.add(
        SaleRecord.create(
            "11",
            LocalDateTime.parse("2026-01-02T10:15:00"),
            "2345",
            "Microsoft Office",
            "Software",
            3,
            new BigDecimal("20.99")));

    Map<String, Set<String>> producNameCategory = service.productNamesByCategory(sales);
    assertAll(
        "Test for the productNameCategory map. size, test of values",
        () -> assertTrue(producNameCategory.get("Hardware").contains("Mouse")),
        () -> assertTrue(producNameCategory.get("Hardware").contains("CPU")),
        () -> assertTrue(producNameCategory.get("Hardware").contains("Teclado")),
        () -> assertEquals(3, producNameCategory.get("Hardware").size()),
        () -> assertEquals(2, producNameCategory.size()),
        () -> assertTrue(producNameCategory.get("Software").contains("Microsoft Office")),
        () -> assertEquals(1, producNameCategory.get("Software").size()));
  }

  @Test
  @DisplayName("Test for the X day with more revenue than 20.00")
  void dayWithMoreRevenueThanTwenty() {
    sales.add(
        SaleRecord.create(
            "11",
            LocalDateTime.parse("2026-01-22T10:15:00"),
            "2345",
            "Teclado",
            "Hardware",
            10,
            new BigDecimal("19.99")));
    List<LocalDate> daysRevenue = service.daysWithRevenueOver(sales, new BigDecimal("20.00"));
    assertAll(
        "Check the list size, and values",
        () -> assertEquals(LocalDate.parse("2026-01-01"), daysRevenue.get(0)),
        () -> assertEquals(LocalDate.parse("2026-01-22"), daysRevenue.get(1)),
        () -> assertEquals(2, daysRevenue.size()));
  }

  @Test
  @DisplayName("Test for the X day with more revenue than -20.00 throw IllegalArgument")
  void dayWithMoreRevenueThanMinusTwenty() {
    Exception ex =
        assertThrows(
            IllegalArgumentException.class,
            () -> service.daysWithRevenueOver(sales, new BigDecimal("-20.00")));
    assertEquals("Threshold must be > 0", ex.getMessage());
  }

  @Test
  @DisplayName("Test for the X day with more revenue than 0 throw IllegalArgument")
  void dayWithMoreRevenueThanZero() {
    Exception ex =
        assertThrows(
            IllegalArgumentException.class,
            () -> service.daysWithRevenueOver(sales, BigDecimal.ZERO));
    assertEquals("Threshold must be > 0", ex.getMessage());
  }

  @Test
  @DisplayName("Partitioning by the 20.00 (true greater than 20.00 and false the others)")
  void partitioningByTwenty() {
    Map<Boolean, List<SaleRecord>> listPartitioned =
        service.partitionOutliers(sales, new BigDecimal("20.00"));
    assertAll(
        "Checks the size, values in the list salerecord with the names",
        () -> assertEquals(2, listPartitioned.size()),
        () -> assertEquals(1, listPartitioned.get(true).size()),
        () -> assertEquals("Mouse", listPartitioned.get(true).get(0).getProductName()));
  }

  @Test
  @DisplayName("Partitioning by the - 20.00  must throw  IllegalArgumentException")
  void partitioningByMinusTwenty() {
    Exception ex =
        assertThrows(
            IllegalArgumentException.class,
            () -> service.partitionOutliers(sales, new BigDecimal("-20.00")));
    assertEquals("Threshold must be > 0", ex.getMessage());
  }

  @Test
  @DisplayName("Partitioning by the 0  must throw  IllegalArgumentException")
  void partitioningByZero() {
    Exception ex =
        assertThrows(
            IllegalArgumentException.class,
            () -> service.partitionOutliers(sales, BigDecimal.ZERO));
    assertEquals("Threshold must be > 0", ex.getMessage());
  }

  @Test
  @DisplayName("Revenue statistics revenue")
  void revenueSatatistics() {
    DoubleSummaryStatistics revenue = service.revenueStats(sales);
    assertAll(
        () -> assertEquals(3, revenue.getCount()),
        () -> assertEquals(199.90, revenue.getMax()),
        () -> assertEquals(10.99, revenue.getMin()),
        () -> assertEquals(221.88, revenue.getSum()),
        () -> assertEquals(73.96, revenue.getAverage()));
  }

  @Test
  @DisplayName("Top products by category (top 1 by category)")
  void topOneProductByCategory() {
    sales.add(
        SaleRecord.create(
            "11",
            LocalDateTime.parse("2026-01-02T10:15:00"),
            "2345",
            "Word",
            "Software",
            3,
            new BigDecimal("20.99")));
    Map<String, List<ProductRevenue>> topProducts = service.topProductsByCategory(sales, 1);
    assertAll(
        () -> assertEquals("Mouse", topProducts.get("Hardware").get(0).productName()),
        () -> assertEquals(new BigDecimal("199.90"), topProducts.get("Hardware").get(0).revenue()),
        () -> assertEquals("Word", topProducts.get("Software").get(0).productName()),
        () -> assertEquals(new BigDecimal("62.97"), topProducts.get("Software").get(0).revenue()),
        () -> assertEquals(2, topProducts.size()));
  }

  @Test
  @DisplayName("Top products by category (top -1 by category) Throw IllegalArgumentException")
  void topOneProductByCategoryMinusOne() {
    Exception ex =
        assertThrows(
            IllegalArgumentException.class, () -> service.topProductsByCategory(sales, -1));
    assertEquals("Top N must be > 0 and <= sales.size()", ex.getMessage());
  }

  @Test
  @DisplayName("Top products by category (top 0 by category) Throw IllegalArgumentException")
  void topOneProductByCategoryZero() {
    Exception ex =
        assertThrows(IllegalArgumentException.class, () -> service.topProductsByCategory(sales, 0));
    assertEquals("Top N must be > 0 and <= sales.size()", ex.getMessage());
  }

  @Test
  @DisplayName("Normalizacion category toLowerCase and trim")
  void normalizacionCategory() {
    sales = new ArrayList<>();
    sales.add(
        SaleRecord.create(
            "11",
            LocalDateTime.parse("2026-01-02T10:15:00"),
            "2345",
            "Teclado",
            "  TEST  ",
            10,
            new BigDecimal("19.99")));

    sales.add(
        SaleRecord.create(
            "11",
            LocalDateTime.parse("2026-01-02T10:15:00"),
            "2345",
            "Teclado",
            "  TesT  ",
            10,
            new BigDecimal("19.99")));
    List<SaleRecord> list = service.normalizacionCategoryTrimUpper(sales);
    assertAll(
        () -> assertEquals("test", list.get(0).getCategory()),
        () -> assertEquals("test", list.get(1).getCategory()));
  }

  @Test
  @DisplayName("Max revenue sale")
  void maxRevenueSale() {
    Optional<SaleRecord> sale = service.maxRevenueSale(sales);
    assertTrue(sale.isPresent());
    assertEquals("Mouse", sale.get().getProductName());
  }

  @Test
  @DisplayName("Total revenue")
  void totalRevenue() {
    Optional<BigDecimal> revenue = service.totalRevenue(sales);
    assertTrue(revenue.isPresent());
    assertTrue(revenue.get().compareTo(new BigDecimal("221.88")) == 0);
  }

  @Test
  @DisplayName("Find by sale id or return an empty optional")
  void findBySaleId() {
    Optional<SaleRecord> sale = service.findBySaleId(sales, "22");
    assertTrue(sale.isPresent());
    assertEquals("Teclado", sale.get().getProductName());
  }

  @ParameterizedTest
  @NullSource
  @DisplayName("Find by sale id with a null sale id throws a IllegalArgumentException")
  void nullFindBySaleId(String n) {
    Exception ex =
        assertThrows(IllegalArgumentException.class, () -> service.findBySaleId(sales, n));
    assertEquals("Sale id must have a value", ex.getMessage());
  }

  @ParameterizedTest
  @EmptySource
  @DisplayName("Find by sale id with a empty sale id throw a IllegalArgumentException")
  void emptyFindBySaleId(String n) {
    Exception ex =
        assertThrows(IllegalArgumentException.class, () -> service.findBySaleId(sales, n));
    assertEquals("Sale id must have a value", ex.getMessage());
  }

  @Test
  @DisplayName("Find by sale id and doesnt find and return a empty optional")
  void emptyOptionalFindBySaleId() {
    Optional<SaleRecord> sale = service.findBySaleId(sales, "22343245");
    assertFalse(sale.isPresent());
  }

  @Test
  @DisplayName("Find the top one product sale by quantity")
  void topProductByQuantity() {
    Optional<ProductQuantity> topOne = service.topProductByQuantity(sales);
    assertTrue(topOne.isPresent());
    assertEquals("Mouse", topOne.get().name());
  }

  @Test
  @DisplayName("Total revenue Hardware category")
  void revenueForCategoryHardware() {
    Optional<BigDecimal> revenue = service.revenueForCategory(sales, "Hardware");
    assertTrue(revenue.isPresent());
    assertTrue(revenue.get().compareTo(new BigDecimal("221.88")) == 0);
  }

  @Test
  @DisplayName("Total revenue with a random category should throw a exception")
  void revenueForCategoryWithRandomCategory() {
    Exception ex =
        assertThrows(
            IllegalArgumentException.class,
            () -> service.revenueForCategoryOrThrow(sales, "Testttt"));
    assertEquals("Category not found", ex.getMessage());
  }

  @ParameterizedTest
  @NullSource
  @DisplayName("Total revenue for category null")
  void nullRevenueForCategory(String n) {
    Exception ex =
        assertThrows(IllegalArgumentException.class, () -> service.revenueForCategory(sales, n));
    assertEquals("Category must have a value", ex.getMessage());
  }

  @ParameterizedTest
  @EmptySource
  @DisplayName("Total revenue for category emptysource")
  void emptyTotalRevenueForCategory(String n) {
    Exception ex =
        assertThrows(IllegalArgumentException.class, () -> service.revenueForCategory(sales, n));
    assertEquals("Category must have a value", ex.getMessage());
  }

  @Test
  @DisplayName("Total revenue with reduce")
  void totalRevenueRedue() {
    assertTrue(service.totalRevenueStrict(sales).compareTo(new BigDecimal("221.88")) == 0);
  }

  @Test
  @DisplayName("Product that genere the min revenue (the first)")
  void minRevenue() {
    Optional<SaleRecord> min = service.minRevenueSale(sales);
    assertFalse(min.isEmpty());
    assertTrue(min.get().getProductName().equals("Teclado"));
  }
}
