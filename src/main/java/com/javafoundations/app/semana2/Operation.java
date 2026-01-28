package com.javafoundations.app.semana2;

/** Operation. */
@FunctionalInterface
public interface Operation {
  /**
   * Operate is a functional method that you pass two numbers And use them to create a operation.
   *
   * @param a int
   * @param b int
   * @return int
   */
  int operate(int a, int b);
}
