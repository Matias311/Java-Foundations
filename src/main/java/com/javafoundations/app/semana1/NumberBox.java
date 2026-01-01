package com.javafoundations.app.semana1;

import java.util.List;

/** NumberBox. */
public class NumberBox<T extends Number> {
  private final T number; // it can not be null

  /**
   * Creates a NumberBox. It can not be null.
   *
   * @param number Number
   */
  public NumberBox(T number) {
    if (number == null) {
      throw new IllegalArgumentException("number cannot be null");
    }
    this.number = number;
  }

  public T getNumber() {
    return number;
  }

  /**
   * Return the number value as a double.
   *
   * @return Double
   */
  public double doubleValue() {
    return number.doubleValue();
  }

  /**
   * Sum the numbers of a list and return the total in a double type value.
   *
   * @param numbers List of number
   * @return double
   */
  public static double sum(List<? extends Number> numbers) {
    double total = 0;

    for (Number number : numbers) {
      total = total + number.doubleValue();
    }

    return total;
  }
}
