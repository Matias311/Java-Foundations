package com.javafoundations.app.semana3.salesanalytics.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

@DisplayName("Test for the SaleRecord class")
public class SaleRecordTest {

  SaleRecord salesRecord;
  LocalDateTime time;

  @BeforeEach
  void setUp() {
    salesRecord = null;
    time = LocalDateTime.now();
  }

  @ParameterizedTest
  @CsvSource({"S-1001, 2026-01-01T10:15:00, P-01, Mouse, Accessories, 2, 19.99"})
  @DisplayName("Creates the salesRecord using a csv source")
  void creatingSalesRecordFromCsvSource(
      String saleId,
      String timestampCsv,
      String productId,
      String productName,
      String category,
      int quantity,
      BigDecimal unitPrice) {
    LocalDateTime timestamp = LocalDateTime.parse(timestampCsv);
    salesRecord =
        SaleRecord.create(saleId, timestamp, productId, productName, category, quantity, unitPrice);

    assertEquals("S-1001", salesRecord.getSaleId(), () -> "The SaleId value its not correct");
    assertEquals(
        LocalDateTime.parse(timestampCsv),
        salesRecord.getTimestamp(),
        () -> "The time value its not correct");
    assertEquals("P-01", salesRecord.getProductId(), () -> "The productId value its not correct");
    assertEquals("Mouse", salesRecord.getProductName(), () -> "The Name value its not correct");
    assertEquals(
        "Accessories", salesRecord.getCategory(), () -> "The Category value its not correct");
    assertEquals(2, salesRecord.getQuantity(), () -> "The Sales Quantity value its not correct");
    assertEquals(
        0,
        salesRecord.getUnitPrice().compareTo(unitPrice),
        () -> "The unit price value is not correct");
  }

  @ParameterizedTest
  @CsvSource({"S-1001, 2026-01-01T10:15:00, P-01, Mouse, Accessories, 2, 19.99"})
  @DisplayName("Creates a salesRecord using a csv source and calculate the revenue")
  void calculatingRevenueFromSalesRecord(
      String saleId,
      String timestampCsv,
      String productId,
      String productName,
      String category,
      int quantity,
      BigDecimal unitPrice) {
    LocalDateTime timestamp = LocalDateTime.parse(timestampCsv);
    BigDecimal result = BigDecimal.valueOf(39.98);
    salesRecord =
        SaleRecord.create(saleId, timestamp, productId, productName, category, quantity, unitPrice);
    assertEquals(0, salesRecord.revenue().compareTo(result));
  }

  @ParameterizedTest
  @CsvSource({"S-1001, 2026-01-01T10:15:00, P-01, Mouse, Accessories, -1, 19.99"})
  @DisplayName(
      "Creates a salesRecord using a csv source and verify the throw exception of a quantity less"
          + " than 0")
  void quantityLessThanZero(
      String saleId,
      String timestampCsv,
      String productId,
      String productName,
      String category,
      int quantity,
      BigDecimal unitPrice) {
    LocalDateTime timestamp = LocalDateTime.parse(timestampCsv);
    Exception ex =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                SaleRecord.create(
                    saleId, timestamp, productId, productName, category, quantity, unitPrice));
    assertEquals("Quantity must be more than 0", ex.getMessage());
  }

  @ParameterizedTest
  @CsvSource({"S-1001, 2026-01-01T10:15:00, P-01, Mouse, Accessories, 10, -10.22"})
  @DisplayName(
      "Creates a salesRecord using a csv source and verify the throw exception of a unit price more"
          + " than 0")
  void unitPriceLessThanZero(
      String saleId,
      String timestampCsv,
      String productId,
      String productName,
      String category,
      int quantity,
      BigDecimal unitPrice) {
    LocalDateTime timestamp = LocalDateTime.parse(timestampCsv);
    Exception ex =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                SaleRecord.create(
                    saleId, timestamp, productId, productName, category, quantity, unitPrice));
    assertEquals("Unit price must be more than 0", ex.getMessage());
  }

  @Nested
  class TestEmptyValues {

    @ParameterizedTest
    @EmptySource
    @DisplayName("Creates a sales record with a empty sale id")
    void createSaleRecordEmptySaleId(String saleId) {
      Exception ex =
          assertThrows(
              IllegalArgumentException.class,
              () ->
                  SaleRecord.create(
                      saleId,
                      LocalDateTime.now(),
                      "P-01",
                      "Mouse",
                      "Accessories",
                      10,
                      new BigDecimal(19.99)));
      assertEquals("The sale id must have a value", ex.getMessage());
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("Creates a sales record with a empty product id")
    void createSaleRecordEmptyProductId(String productId) {
      Exception ex =
          assertThrows(
              IllegalArgumentException.class,
              () ->
                  SaleRecord.create(
                      "sis",
                      LocalDateTime.now(),
                      productId,
                      "Mouse",
                      "Accessories",
                      10,
                      new BigDecimal(19.99)));
      assertEquals("The product id must have a value", ex.getMessage());
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("Creates a sales record with a empty product name")
    void createSaleRecordEmptyProductName(String productName) {
      Exception ex =
          assertThrows(
              IllegalArgumentException.class,
              () ->
                  SaleRecord.create(
                      "sis",
                      LocalDateTime.now(),
                      "22",
                      productName,
                      "Accessories",
                      10,
                      new BigDecimal(19.99)));
      assertEquals("The product name must have a value", ex.getMessage());
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("Creates a sales record with a empty category")
    void createSaleRecordEmptyCategory(String category) {
      Exception ex =
          assertThrows(
              IllegalArgumentException.class,
              () ->
                  SaleRecord.create(
                      "sis",
                      LocalDateTime.now(),
                      "22",
                      "Mouse",
                      category,
                      10,
                      new BigDecimal(19.99)));
      assertEquals("The product category must have a value", ex.getMessage());
    }
  }

  @Nested
  class TestNullValues {
    @ParameterizedTest
    @NullSource
    @DisplayName("Creates a sales record with a null sale id")
    void createSaleRecordNullSaleId(String saleId) {
      Exception ex =
          assertThrows(
              IllegalArgumentException.class,
              () ->
                  SaleRecord.create(
                      saleId,
                      LocalDateTime.now(),
                      "P-01",
                      "Mouse",
                      "Accessories",
                      10,
                      new BigDecimal(19.99)));
      assertEquals("The sale id must have a value", ex.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Creates a sales record with a null timestamp")
    void createSaleRecordNullTimesTamp(LocalDateTime timestampCsv) {
      Exception ex =
          assertThrows(
              IllegalArgumentException.class,
              () ->
                  SaleRecord.create(
                      "sisis",
                      timestampCsv,
                      "P-01",
                      "Mouse",
                      "Accessories",
                      10,
                      new BigDecimal(19.99)));
      assertEquals("The time must have a value", ex.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Creates a sales record with a null product id")
    void createSaleRecordNullProductId(String productId) {
      Exception ex =
          assertThrows(
              IllegalArgumentException.class,
              () ->
                  SaleRecord.create(
                      "sisis",
                      LocalDateTime.now(),
                      productId,
                      "Mouse",
                      "Accessories",
                      10,
                      new BigDecimal(19.99)));
      assertEquals("The product id must have a value", ex.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Creates a sales record with a null product name")
    void createSaleRecordNullProductName(String productName) {
      Exception ex =
          assertThrows(
              IllegalArgumentException.class,
              () ->
                  SaleRecord.create(
                      "sisis",
                      LocalDateTime.now(),
                      "si",
                      productName,
                      "Accessories",
                      10,
                      new BigDecimal(19.99)));
      assertEquals("The product name must have a value", ex.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Creates a sales record with a null product category")
    void createSaleRecordNullCategory(String category) {
      Exception ex =
          assertThrows(
              IllegalArgumentException.class,
              () ->
                  SaleRecord.create(
                      "sisis",
                      LocalDateTime.now(),
                      "si",
                      "Mouse",
                      category,
                      10,
                      new BigDecimal(19.99)));
      assertEquals("The product category must have a value", ex.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Creates a sales record with a null product unit price")
    void createSaleRecordNullUnitPrice(BigDecimal unitPrice) {
      Exception ex =
          assertThrows(
              IllegalArgumentException.class,
              () ->
                  SaleRecord.create(
                      "sisis", LocalDateTime.now(), "si", "Mouse", "Accessories", 10, unitPrice));
      assertEquals("The product unit price must have a value", ex.getMessage());
    }
  }
}
