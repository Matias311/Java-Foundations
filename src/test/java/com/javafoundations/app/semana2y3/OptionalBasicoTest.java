package com.javafoundations.app.semana2y3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("test for the optional basic")
public class OptionalBasicoTest {

  private OptionalBasico method;

  @BeforeEach
  void setUp() {
    this.method = new OptionalBasico();
  }

  @Test
  @DisplayName("Normalize with null value")
  void nullNormalize() {
    assertEquals(Optional.empty(), method.normalize(null));
  }

  @Test
  @DisplayName("Normalize with a string with spaces in the start")
  void spacesStartStringNormalize() {
    Optional<String> value = method.normalize("   hola");
    assertEquals("hola", value.get());
  }

  @Test
  @DisplayName("Normalize with a empty string")
  void emptyStringNormalize() {
    assertEquals(Optional.empty(), method.normalize("  "));
  }
}
