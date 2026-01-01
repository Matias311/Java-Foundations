package com.javafoundations.app.semana1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** NumberBoxTest. */
@DisplayName("Tests for number box class")
public class NumberBoxTest {

  @Test
  @DisplayName("Test for method doubleValue() with integer")
  void doubleValueWorksForInteger() {
    NumberBox<Integer> ni = new NumberBox<>(42);
    assertEquals(42.0, ni.doubleValue());
  }

  @Test
  @DisplayName("Test for method doubleValue() with double")
  void doubleValueWorksForDouble() {
    NumberBox<Double> nd = new NumberBox<>(3.2);
    assertEquals(3.2, nd.doubleValue());
  }

  @Test
  @DisplayName("Test for the construct can not accept null")
  void constructorRejectsNull() {
    Exception except = assertThrows(IllegalArgumentException.class, () -> new NumberBox<>(null));
    assertEquals("number cannot be null", except.getMessage());
  }

  @Test
  @DisplayName("Test for the sum method")
  void sumMethodWithListOfNumbers() {
    List<Integer> list = new ArrayList<>(List.of(10, 20, 30));
    assertEquals(60.0, NumberBox.sum(list));
  }
}
