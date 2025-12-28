package com.javafoundations.app.semana1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test for calculator class")
public class CalculatorTest {

  private static Calculator calculator;

  @BeforeAll
  static void setUp() {
    calculator = new Calculator();
  }

  @Test
  @DisplayName("Sum two by two and verify the result")
  void sumTwoByTwo() {
    assertEquals(4, calculator.add(2, 2), () -> "Error, the result is: " + calculator.add(2, 2));
  }

  @Test
  @DisplayName("Sum (minus) two by two and verify the result")
  void sumMinusTwoByTwo() {
    assertEquals(0, calculator.add(-2, 2), () -> "Error, the result is: " + calculator.add(-2, 2));
  }

  @Test
  @DisplayName("Subtract zero by two and verify the result")
  void subtractZeroByTwo() {
    assertEquals(
        -2,
        calculator.subtraction(0, 2),
        () -> "Error, the result is: " + calculator.subtraction(0, 2));
  }

  @Test
  @DisplayName("Subtract (negative) two by (negative) four")
  void subtractNegativeTwoByNegativeFour() {
    assertEquals(
        2,
        calculator.subtraction(-2, -4),
        () -> "Error, the result is: " + calculator.subtraction(-2, -4));
  }

  @Test
  @DisplayName("Multiply six by four and verify the result")
  void multiplySixByFour() {
    assertEquals(
        24,
        calculator.multiplication(6, 4),
        () -> "Error, the result is: " + calculator.multiplication(6, 4));
  }

  @Test
  @DisplayName("Division of six by three and verify the result")
  void divisionSixByThree() {
    assertEquals(
        2, calculator.division(6, 3), () -> "Error: the result is " + calculator.division(6, 3));
  }

  @Test
  @DisplayName("Division by zero (six divide by zero)")
  void divisionSixByZeroErrorException() {
    Exception exception = assertThrows(ArithmeticException.class, () -> calculator.division(6, 0));
    assertEquals("/ by zero", exception.getMessage());
  }
}
