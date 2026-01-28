package com.javafoundations.app.semana2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** CalculatorFunctionalInterfacesTest. */
@DisplayName("Test for the functional interface of calculatior")
public class CalculatorFunctionalInterfacesTest {
  CalculatorFunctionalInterfaces calculator;
  int result;

  @BeforeEach
  void setUp() {
    calculator = new CalculatorFunctionalInterfaces();
    result = 0;
  }

  @Test
  @DisplayName("Operate with sum")
  void sumWithFunctionalInterface() {
    result = calculator.operateAndPrint(2, 2, (a, b) -> a + b);
    assertEquals(4, result);
  }

  @Test
  @DisplayName("Operate with subtraction")
  void subtractionWithFunctionalInterface() {
    result = calculator.operateAndPrint(2, 2, (a, b) -> a - b);
    assertEquals(0, result);
  }

  @Test
  @DisplayName("Operate with multiplication")
  void multiplicationWithFunctionalInterface() {
    result = calculator.operateAndPrint(6, 2, (a, b) -> a * b);
    assertEquals(12, result);
  }

  @Test
  @DisplayName("Operate with division")
  void divisionWithFunctionalInterface() {
    result = calculator.operateAndPrint(6, 2, (a, b) -> a / b);
    assertEquals(3, result);
  }

  @Test
  @DisplayName("Operate with division by zero")
  void divisionByZeroWithFunctionalInterface() {
    Exception ex =
        assertThrows(
            ArithmeticException.class, () -> calculator.operateAndPrint(2, 0, (a, b) -> a / b));
    assertEquals("/ by zero", ex.getMessage());
  }
}
