package com.javafoundations.app.semana3.salesanalytics.service;

import com.javafoundations.app.semana3.salesanalytics.model.ProductRevenue;
import com.javafoundations.app.semana3.salesanalytics.model.SaleRecord;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
        .map(salerecord -> new ProductRevenue(salerecord.getKey(), salerecord.getValue().abs()))
        .sorted(Comparator.comparing(ProductRevenue::revenue).reversed())
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

  public Set<String> duplicateSaleIds(List<SaleRecord> sales) {
    return sales.stream()
        .collect(Collectors.groupingBy(SaleRecord::getSaleId, Collectors.counting()))
        .entrySet()
        .stream()
        .filter(entry -> entry.getValue() > 1)
        .map(Map.Entry::getKey)
        .collect(Collectors.toSet());
  }

  public List<SaleRecord> findRevenueOutliners(List<SaleRecord> sales, BigDecimal threshold) {
    if (threshold.compareTo(BigDecimal.ZERO) == -1) {
      throw new IllegalArgumentException("The threshold must be >= 0");
    }
    return sales.stream().filter(sale -> sale.revenue().compareTo(threshold) > 0).toList();
  }
}
