package com.javafoundations.app.semana2.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Test for InMemoryRepository")
public class InMemoryRepositoryTest {
  private InMemoryRepository<String, String> repo;

  @BeforeEach
  void setUp() {
    repo = new InMemoryRepository<>();
  }

  @Test
  @DisplayName("Find by id with a empty storage")
  void findByIdReturnEmptyOptional() {
    assertEquals(Optional.empty(), repo.findById("1"));
  }

  @Test
  @DisplayName("GetOrThrow check exception when its missing the value")
  void getOrThrowThrowsWhenMissing() {
    String key = "2";
    Exception ex = assertThrows(NotFoundException.class, () -> repo.getOrThrow(key));
    assertEquals("It cannot get the value: " + key, ex.getMessage());
  }

  @ParameterizedTest
  @DisplayName("Save values with a CSV and check the return value with the key")
  @CsvSource({"1,HOLA", "2,OK", "3,SISIS"})
  void saveThenFindReturnsValue(String k, String v) {
    repo.save(k, v);
    assertEquals(v, repo.getOrThrow(k));
  }

  @Test
  @DisplayName("Save and checks if exists")
  void saveAndChecksIfReturnTrue() {
    repo.save("1", "Hola");
    assertTrue(repo.exists("1"));
  }

  @Test
  @DisplayName("Checks if exists a empty storage")
  void checksIfExistsInAemptyStorage() {
    assertFalse(repo.exists("1"));
  }

  @Test
  @DisplayName("Tries to delete with empty storage")
  void deleteWithEmptyStorage() {
    assertFalse(repo.delete("1"));
  }

  @Test
  @DisplayName("Save and delete with the key")
  void saveAndDeleteWithKey() {
    repo.save("1", "Hola");
    repo.save("2", "SISISIS");
    assertTrue(repo.delete("1"));
    assertTrue(repo.delete("2"));
  }

  @Nested
  class TestKeyNull {
    @Test
    @DisplayName("Find by id with a null key")
    void findByIdWithNullKey() {
      Exception ex = assertThrows(IllegalArgumentException.class, () -> repo.findById(null));
      assertEquals("Key cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("Get or Throw with null key")
    void getOrThrowNullKey() {
      Exception ex = assertThrows(IllegalArgumentException.class, () -> repo.getOrThrow(null));
      assertEquals("Key cannot be null", ex.getMessage());
    }
  }

  @Test
  @DisplayName("Save with key and null value")
  void saveWithNullValue() {
    Exception ex = assertThrows(IllegalArgumentException.class, () -> repo.save("1", null));
    assertEquals("Value cannot be null", ex.getMessage(), () -> "Error: the value should be null");
  }

  @Test
  @DisplayName("Save with null key and value")
  void saveWithValueAndNullKey() {
    Exception ex = assertThrows(IllegalArgumentException.class, () -> repo.save(null, "Hola"));
    assertEquals("Key cannot be null", ex.getMessage(), () -> "Error: the key should be null");
  }

  @Test
  @DisplayName("Exists with null key")
  void existWithNullKey() {
    Exception ex = assertThrows(IllegalArgumentException.class, () -> repo.exists(null));
    assertEquals("Key cannot be null", ex.getMessage(), () -> "Error: the key should be null");
  }

  @Test
  @DisplayName("Delete with null key")
  void deleteWithNullKey() {
    Exception ex = assertThrows(IllegalArgumentException.class, () -> repo.delete(null));
    assertEquals("Key cannot be null", ex.getMessage(), () -> "Error: the key should be null");
  }
}
