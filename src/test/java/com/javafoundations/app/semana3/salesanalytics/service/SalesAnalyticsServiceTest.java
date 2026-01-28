package com.javafoundations.app.semana3.salesanalytics.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.javafoundations.app.semana3.salesanalytics.model.ProductRevenue;
import com.javafoundations.app.semana3.salesanalytics.model.SaleRecord;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        () -> assertEquals(2, duplicate.size()));
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
}
