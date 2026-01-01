package com.javafoundations.app.semana1;

/** Box. */
public class Box<T> {
  T value;

  /**
   * Create a box with a generic / it can be null.
   *
   * @param value Generic
   */
  public Box(T value) {
    this.value = value;
  }

  /** Creates a empty box. */
  public Box() {}

  /**
   * Factory method to create a box.
   *
   * @param <T> We say to the method we gonna use a genic
   * @param value Generic Parameter
   * @return generic Box
   */
  public static <T> Box<T> of(T value) {
    return new Box<T>(value);
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  /**
   * value equals null return true not null return false.
   *
   * @return Boolean
   */
  public boolean isEmpty() {
    return value == null ? true : false;
  }
}
