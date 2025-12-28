package com.javafoundations.app.semana1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test for calculator class")
public class CalculatorTest {

  private Calculator calculator;

  @BeforeEach
  void setUp() {
    this.calculator = new Calculator();
  }

  @Test
  @DisplayName("Sum two by two and verify the result")
  void sumTwoByTwo() {
    int result = calculator.add(2, 2);
    assertEquals(4, result, () -> "Error, the result is: " + result);
  }

  @Test
  @DisplayName("Sum (minus) two by two and verify the result")
  void sumMinusTwoByTwo() {
    int result = calculator.add(-2, 2);
    assertEquals(0, result, () -> "Error, the result is: " + result);
  }

  @Test
  @DisplayName("Subtract zero by two and verify the result")
  void subtractZeroByTwo() {
    int result = calculator.subtraction(0, 2);
    assertEquals(-2, result, () -> "Error, the result is: " + result);
  }

  @Test
  @DisplayName("Subtract (negative) two by (negative) four")
  void subtractNegativeTwoByNegativeFour() {
    int result = calculator.subtraction(-2, -4);
    assertEquals(2, result, () -> "Error, the result is: " + result);
  }

  @Test
  @DisplayName("Multiply six by four and verify the result")
  void multiplySixByFour() {
    int result = calculator.multiplication(6, 4);
    assertEquals(24, result, () -> "Error, the result is: " + result);
  }

  @Test
  @DisplayName("Division of six by three and verify the result")
  void divisionSixByThree() {
    int result = calculator.division(6, 3);
    assertEquals(2, result, () -> "Error: the result is " + result);
  }

  @Test
  @DisplayName("Division by zero (six divide by zero)")
  void divisionSixByZeroErrorException() {
    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> calculator.division(6, 0));
    assertEquals("Division by zero", exception.getMessage());
  }
}
