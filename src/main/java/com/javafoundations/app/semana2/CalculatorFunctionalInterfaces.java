package com.javafoundations.app.semana2;

/** CalculatorFuncntionalInterfaces. */
public class CalculatorFunctionalInterfaces {

  /**
   * Operate uses a funcional interface that have a method operate that you pass two argument (int
   * both) and the method use that to create operation.
   *
   * @param a int
   * @param b int
   * @param op Operation
   * @return int
   */
  public int operateAndPrint(int a, int b, Operation op) {
    int value = op.operate(a, b);
    System.out.println(value);
    return value;
  }
}
