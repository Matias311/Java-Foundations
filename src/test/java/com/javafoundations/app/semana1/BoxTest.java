package com.javafoundations.app.semana1;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** BoxTest. */
@DisplayName("Test for box class")
public class BoxTest {
  Box<Object> box;

  @BeforeEach
  void setUp() {
    this.box = new Box<Object>();
  }

  @Test
  @DisplayName("Test for default constructor. Creates a empty box")
  void defaultBoxStartsEmpty() {
    assertAll(
        "isEmpty() should be true and get() should be null",
        () -> assertTrue(box.isEmpty(), () -> "Error, the box have a value"),
        () -> assertNull(box.getValue(), () -> "Error, the box have a value"));
  }

  @Test
  @DisplayName("Test for setting a value and get the correct value")
  void canStoreAndRetrieveValue() {
    String newValue = "This is a value pass by a setter";
    box.setValue(newValue);
    assertEquals(
        newValue,
        box.getValue(),
        () -> "Error, the value that return getValue() its not " + newValue);
  }

  @Test
  @DisplayName("Test to see if i can store a null value in a box")
  void canStoreNullValue() {
    box.setValue(null); // Pass implicit
    assertNull(box.getValue(), () -> "Error, we cant pass the null value");
  }

  @Test
  @DisplayName("Create a box using a factory method and verify the type, and the value")
  void factoryMethodBox() {
    Box<Integer> box = Box.of(20);
    assertInstanceOf(
        Integer.class, box.getValue(), () -> "Error with the value, its not a integer");
    assertEquals(20, box.getValue(), () -> "Error, doesnt match the value");
  }
}
