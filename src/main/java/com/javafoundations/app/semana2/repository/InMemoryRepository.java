package com.javafoundations.app.semana2.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<K, V> implements Repository<K, V> {

  private final Map<K, V> storage = new HashMap<>();

  @Override
  public Optional<V> findById(K key) {
    verifyKey(key);
    return storage.containsKey(key) ? Optional.of(storage.get(key)) : Optional.empty();
  }

  @Override
  public V getOrThrow(K key) {
    return findById(key)
        .orElseThrow(() -> new NotFoundException("It cannot get the value: " + key));
  }

  @Override
  public void save(K key, V value) {
    verifyKey(key);
    verifyValue(value);
    storage.put(key, value);
  }

  @Override
  public boolean exists(K key) {
    verifyKey(key);
    return storage.containsKey(key);
  }

  @Override
  public boolean delete(K key) {
    verifyKey(key);
    if (storage.containsKey(key)) {
      storage.remove(key);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Checks if the key its not null if its null throw a IllegalArgumentException, if its not null
   * return true.
   *
   * @param key K Generic
   * @return Boolean
   */
  private void verifyKey(K key) {
    if (key == null) {
      throw new IllegalArgumentException("Key cannot be null");
    }
  }

  /**
   * Checks if the value its not null if its null throw a IllegalArgumentException, if its not null
   * return true.
   *
   * @param value V Generic
   * @return Boolean
   */
  private void verifyValue(V value) {
    if (value == null) {
      throw new IllegalArgumentException("Value cannot be null");
    }
  }
}
