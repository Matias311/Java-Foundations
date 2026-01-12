package com.javafoundations.app.semana2.repository;

import java.util.Optional;

public interface Repository<K, V> {

  /**
   * Find the value in storage if it cannot find returns a empty optional.
   *
   * @param key K Generic value if you pass a null key throws a IllegalArgumentException
   * @return A optional with the value or a empty optional
   */
  Optional<V> findById(K key);

  /**
   * Return the value if cannot get the value throws a NotFoundException.
   *
   * @param key K Generic value if you pass a null key throws a IllegalArgumentException
   * @return V Generic value
   */
  V getOrThrow(K key);

  /**
   * Save or replace a existing value in the storage. If you pass key or value null it will throw a
   * IllegalArgumentException.
   *
   * @param key K Generic Value if you pass a null key throws a IllegalArgumentException
   * @param value V Generic Value if you pass a null value throws a IllegalArgumentException
   */
  void save(K key, V value);

  /**
   * Return true if existis in the storage else return false. If you pass a key value null throws a
   * IllegalArgumentException.
   *
   * @param key K Generic Value if you pass a null key throws a IllegalArgumentException
   * @return Boolean value
   */
  boolean exists(K key);

  /**
   * Delete a value using the key, if you pass a null key it wil throw a IllegalArgumentException,
   * return true if its deleted else return false.
   *
   * @param key K Generic Value if you pass a null key throws a IllegalArgumentException
   * @return Boolean
   */
  boolean delete(K key);
}
