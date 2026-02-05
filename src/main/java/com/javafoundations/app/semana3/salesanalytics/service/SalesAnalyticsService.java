package com.javafoundations.app.semana3.salesanalytics.service;

import com.javafoundations.app.semana3.salesanalytics.model.CategoryRevenue;
import com.javafoundations.app.semana3.salesanalytics.model.ProductQuantity;
import com.javafoundations.app.semana3.salesanalytics.model.ProductRevenue;
import com.javafoundations.app.semana3.salesanalytics.model.SaleRecord;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SalesAnalyticsService {

  public Map<String, BigDecimal> revenueByCategory(List<SaleRecord> sales) {
    return sales.stream()
        .collect(
            Collectors.groupingBy(
                SaleRecord::getCategory,
                Collectors.reducing(BigDecimal.ZERO, SaleRecord::revenue, BigDecimal::add)));
  }

  public List<ProductRevenue> topProductByRevenue(List<SaleRecord> sales, int topN) {
    if (topN <= 0) {
      throw new IllegalArgumentException("The topN value can not be zero >= 0");
    }

    if (topN > sales.size()) {
      throw new IllegalArgumentException("The topN value can not be more than the sales list size");
    }

    return sales.stream()
        .collect(
            Collectors.groupingBy(
                SaleRecord::getProductName,
                Collectors.reducing(BigDecimal.ZERO, SaleRecord::revenue, BigDecimal::add)))
        .entrySet()
        .stream()
        .map(salerecord -> new ProductRevenue(salerecord.getKey(), salerecord.getValue()))
        .sorted(
            Comparator.comparing(ProductRevenue::revenue)
                .reversed()
                .thenComparing(ProductRevenue::productName))
        .limit(topN)
        .toList();
  }

  public Map<LocalDate, BigDecimal> revenueByDay(List<SaleRecord> sales) {
    return sales.stream()
        .collect(Collectors.toMap(SaleRecord::getLocalDate, SaleRecord::revenue, BigDecimal::add));
  }

  public Map<YearMonth, BigDecimal> revenueByMonth(List<SaleRecord> sales) {
    return sales.stream()
        .collect(Collectors.toMap(SaleRecord::getYearMonth, SaleRecord::revenue, BigDecimal::add));
  }

  // public Set<String> duplicateSaleIds(List<SaleRecord> sales) {
  //   return sales.stream().map(SaleRecord::getSaleId).collect(Collectors.toSet());
  // }

  public Set<String> duplicateSaleIds(List<SaleRecord> sales) {
    return sales.stream()
        .collect(Collectors.groupingBy(SaleRecord::getSaleId, Collectors.counting()))
        .entrySet()
        .stream()
        .filter(e -> e.getValue() > 1)
        .map(Map.Entry::getKey)
        .collect(Collectors.toSet());
  }

  public List<SaleRecord> findRevenueOutliners(List<SaleRecord> sales, BigDecimal threshold) {
    if (threshold.compareTo(BigDecimal.ZERO) == -1) {
      throw new IllegalArgumentException("The threshold must be >= 0");
    }
    return sales.stream().filter(sale -> sale.revenue().compareTo(threshold) > 0).toList();
  }

  /**
   * Obtain the revenue by the product id.
   *
   * @param sales List of SaleRecord
   * @return A Map String, BigDecimal String: product id BigDecimal: revenue total
   */
  public Map<String, BigDecimal> revenueByProductId(List<SaleRecord> sales) {
    return sales.stream()
        .collect(Collectors.toMap(SaleRecord::getProductId, SaleRecord::revenue, BigDecimal::add));
  }

  public Map<String, Integer> quantityByCategory(List<SaleRecord> sales) {
    return sales.stream()
        .collect(Collectors.toMap(SaleRecord::getCategory, SaleRecord::getQuantity, Integer::sum));
  }

  public List<CategoryRevenue> topCategoriesByRevenue(List<SaleRecord> sales, int topN) {
    if (topN <= 0 || topN > sales.size()) {
      throw new IllegalArgumentException("Top N must be > 0 and must be < sales.size");
    }
    return sales.stream()
        .collect(
            Collectors.groupingBy(
                SaleRecord::getCategory,
                Collectors.reducing(BigDecimal.ZERO, SaleRecord::revenue, BigDecimal::add)))
        .entrySet()
        .stream()
        .map(v -> new CategoryRevenue(v.getKey(), v.getValue()))
        .sorted(
            Comparator.comparing(CategoryRevenue::revenue)
                .reversed()
                .thenComparing(CategoryRevenue::category))
        .limit(topN)
        .toList();
  }

  public Map<Integer, BigDecimal> revenueByHour(List<SaleRecord> sales) {
    return sales.stream()
        .collect(Collectors.toMap(SaleRecord::getHour, SaleRecord::revenue, BigDecimal::add));
  }

  public Map<String, Set<String>> productNamesByCategory(List<SaleRecord> sales) {
    return sales.stream()
        .collect(
            Collectors.groupingBy(
                SaleRecord::getCategory,
                Collectors.mapping(SaleRecord::getProductName, Collectors.toSet())));
  }

  public List<LocalDate> daysWithRevenueOver(List<SaleRecord> sales, BigDecimal threshold) {
    if (threshold.compareTo(BigDecimal.ZERO) == -1 || threshold.compareTo(BigDecimal.ZERO) == 0) {
      throw new IllegalArgumentException("Threshold must be > 0");
    }

    return sales.stream()
        .filter(s -> s.revenue().compareTo(threshold) == 1)
        .map(SaleRecord::getLocalDate)
        .toList();
  }

  public Map<Boolean, List<SaleRecord>> partitionOutliers(
      List<SaleRecord> sales, BigDecimal threshold) {
    if (threshold.compareTo(BigDecimal.ZERO) == -1 || threshold.compareTo(BigDecimal.ZERO) == 0) {
      throw new IllegalArgumentException("Threshold must be > 0");
    }

    return sales.stream()
        .collect(Collectors.partitioningBy(s -> s.revenue().compareTo(threshold) == 1));
  }

  public DoubleSummaryStatistics revenueStats(List<SaleRecord> sales) {
    return sales.stream().mapToDouble(sale -> sale.revenue().doubleValue()).summaryStatistics();
  }

  public Map<String, List<ProductRevenue>> topProductsByCategory(List<SaleRecord> sales, int topN) {

    if (topN >= sales.size() || topN <= 0) {
      throw new IllegalArgumentException("Top N must be > 0 and <= sales.size()");
    }

    return sales.stream()
        .collect(
            Collectors.groupingBy(
                SaleRecord::getCategory,
                Collectors.collectingAndThen(
                    Collectors.mapping(
                        sale -> new ProductRevenue(sale.getProductName(), sale.revenue()),
                        Collectors.toList()),
                    list ->
                        list.stream()
                            .sorted(Comparator.comparing(ProductRevenue::revenue).reversed())
                            .limit(topN)
                            .toList())));
  }

  public List<SaleRecord> normalizacionCategoryTrimUpper(List<SaleRecord> sales) {
    return sales.stream()
        .map(
            s ->
                SaleRecord.create(
                    s.getSaleId(),
                    s.getTimestamp(),
                    s.getProductId(),
                    s.getProductName(),
                    s.getCategory().trim().toLowerCase(),
                    s.getQuantity(),
                    s.getUnitPrice()))
        .collect(Collectors.toList());
  }

  public Optional<SaleRecord> maxRevenueSale(List<SaleRecord> sales) {
    return sales.stream().max(Comparator.comparing(SaleRecord::revenue));
  }

  public Optional<BigDecimal> totalRevenue(List<SaleRecord> sales) {
    return sales.stream().map(SaleRecord::revenue).reduce(BigDecimal::add);
  }

  public Optional<SaleRecord> findBySaleId(List<SaleRecord> sales, String saleId) {
    if (saleId == null || saleId.isBlank()) {
      throw new IllegalArgumentException("Sale id must have a value");
    }
    return sales.stream().filter(s -> s.getSaleId().equals(saleId)).findFirst();
  }

  public Optional<ProductQuantity> topProductByQuantity(List<SaleRecord> sales) {
    return sales.stream()
        .collect(
            Collectors.groupingBy(
                SaleRecord::getProductName,
                Collectors.reducing(0, SaleRecord::getQuantity, Integer::sum)))
        .entrySet()
        .stream()
        .map(h -> new ProductQuantity(h.getKey(), h.getValue()))
        .max(Comparator.comparing(ProductQuantity::quantity));
  }

  public Optional<BigDecimal> revenueForCategory(List<SaleRecord> sales, String category) {
    if (category == null || category.isEmpty()) {
      throw new IllegalArgumentException("Category must have a value");
    }
    return sales.stream()
        .filter(s -> s.getCategory().equals(category))
        .map(SaleRecord::revenue)
        .reduce((r, ra) -> r.add(ra));
  }

  public BigDecimal revenueForCategoryOrThrow(List<SaleRecord> sales, String category) {
    return revenueForCategory(sales, category)
        .orElseThrow(() -> new IllegalArgumentException("Category not found"));
  }

  public BigDecimal totalRevenueStrict(List<SaleRecord> sales) {
    return sales.stream().map(SaleRecord::revenue).reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public Optional<SaleRecord> minRevenueSale(List<SaleRecord> sales) {
    return sales.stream().min(Comparator.comparing(SaleRecord::revenue));
  }
}
