package com.javafoundations.app.semana3.salesanalytics.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Objects;

public final class SaleRecord {
  private String saleId;
  private LocalDateTime timestamp;
  private String productId;
  private String productName;
  private String category;
  private int quantity;
  private BigDecimal unitPrice;

  private SaleRecord(
      String saleId,
      LocalDateTime timestamp,
      String productId,
      String productName,
      String category,
      int quantity,
      BigDecimal unitPrice) {
    this.saleId = saleId;
    this.timestamp = timestamp;
    this.productId = productId;
    this.productName = productName;
    this.category = category;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }

  /**
   * Creates and verify a SaleRecord, if the string values are empty throw a
   * IllegalArgumentException or a value is null throw a NullPointerException.
   *
   * @param saleId String if the value is empty throw a IllegalArgumentException or if its a null
   *     throw a NullPointerException
   * @param timestamp LocalDateTime if the value its a null throw a NullPointerException
   * @param productId String if the value is empty throw a IllegalArgumentException or if its a null
   *     throw a NullPointerException
   * @param productName String if the value is empty throw a IllegalArgumentException or if its a
   *     null throw a NullPointerException
   * @param category String if the value is empty throw a IllegalArgumentException or if its a null
   *     throw a NullPointerException
   * @param quantity int if the value is 0 or less than 0 throw a IllegalArgumentException
   * @param unitPrice BigDecimal if the value is 0 or less than 0 throw a IllegalArgumentException
   * @return SaleRecord
   */
  public static SaleRecord create(
      String saleId,
      LocalDateTime timestamp,
      String productId,
      String productName,
      String category,
      int quantity,
      BigDecimal unitPrice) {
    if (saleId == null || saleId.isBlank()) {
      throw new IllegalArgumentException("The sale id must have a value");
    }

    if (productId == null || productId.isBlank()) {
      throw new IllegalArgumentException("The product id must have a value");
    }
    if (productName == null || productName.isBlank()) {
      throw new IllegalArgumentException("The product name must have a value");
    }

    if (category == null || category.isBlank()) {
      throw new IllegalArgumentException("The product category must have a value");
    }
    if (unitPrice == null) {
      throw new IllegalArgumentException("The product unit price must have a value");
    }

    if (timestamp == null) {
      throw new IllegalArgumentException("The time must have a value");
    }

    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be more than 0");
    }
    if (unitPrice.signum() == -1 || unitPrice.signum() == 0) {
      throw new IllegalArgumentException("Unit price must be more than 0");
    }

    return new SaleRecord(saleId, timestamp, productId, productName, category, quantity, unitPrice);
  }

  /**
   * Calculate the revenue unit price * quantity.
   *
   * @return BigDecimal value
   */
  public BigDecimal revenue() {
    return unitPrice.multiply(BigDecimal.valueOf(quantity));
  }

  public String getSaleId() {
    return saleId;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public int getHour() {
    return timestamp.getHour();
  }

  // @Override
  // public int hashCode() {
  //   return Objects.hash(saleId);
  // }
  //
  // @Override
  // public boolean equals(Object obj) {
  //   if (this == obj) return true;
  //   if (obj == null) return false;
  //   if (getClass() != obj.getClass()) return false;
  //   if (!(obj instanceof SaleRecord)) return false;
  //   SaleRecord that = (SaleRecord) obj;
  //   return Objects.equals(saleId, that.saleId);
  // }

  public LocalDate getLocalDate() {
    return timestamp.toLocalDate();
  }

  @Override
  public int hashCode() {
    return Objects.hash(saleId);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof SaleRecord)) return false;
    SaleRecord other = (SaleRecord) obj;
    return Objects.equals(this.saleId, other.saleId);
  }

  public YearMonth getYearMonth() {
    return YearMonth.of(timestamp.getYear(), timestamp.getMonth());
  }

  public String getProductId() {
    return productId;
  }

  public String getProductName() {
    return productName;
  }

  public String getCategory() {
    return category;
  }

  public int getQuantity() {
    return quantity;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }
}
