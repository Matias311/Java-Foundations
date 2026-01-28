package com.javafoundations.app.semana3.salesanalytics;

import com.javafoundations.app.semana3.salesanalytics.model.SaleRecord;
import com.javafoundations.app.semana3.salesanalytics.parser.ParserCsv;
import com.javafoundations.app.semana3.salesanalytics.service.SalesAnalyticsService;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.List;

public class SalesAnalyticsApp {
  private static ParserCsv parser = new ParserCsv();
  private static SalesAnalyticsService service = new SalesAnalyticsService();

  public static void main(String[] args) {

    if (args.length < 2) {
      System.out.println("Usage:");
      System.out.println("--input <file> [--topN <number>] [--outlierThreshold <number>]");
      return;
    }

    String input = null;
    int topN = 5;
    BigDecimal outlier = new BigDecimal("10000");

    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "--input":
          input = args[++i];
          break;

        case "--topN":
          topN = Integer.parseInt(args[++i]);
          break;

        case "--outlierThreshold":
          outlier = new BigDecimal(args[++i]);
          break;

        default:
          System.out.println("Unknown argument: " + args[i]);
          return;
      }
    }

    if (input == null || input.isBlank()) {
      System.out.println("Input file is required.");
      return;
    }

    try {
      List<SaleRecord> sales = parser.parse(new FileReader(input));

      System.out.println(service.revenueByCategory(sales));
      System.out.println(service.topProductByRevenue(sales, topN));
      System.out.println(service.revenueByDay(sales));
      System.out.println(service.revenueByMonth(sales));
      System.out.println(service.duplicateSaleIds(sales));
      System.out.println(service.findRevenueOutliners(sales, outlier));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
