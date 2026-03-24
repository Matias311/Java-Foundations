package com.javafoundations.app.semana7;

import java.util.concurrent.Callable;

public class FutureWithCallableExample implements Callable<Integer> {

  private String operation;
  private int n1;
  private int n2;

  public FutureWithCallableExample(String operation, int n1, int n2) {
    this.operation = operation;
    this.n1 = n1;
    this.n2 = n2;
  }

  @Override
  public Integer call() throws Exception {
    int result = 0;

    switch (operation) {
      case "sum" -> result = n1 + n2;
      case "subtraction" -> result = n1 - n2;
      default -> System.out.println("Error");
    }

    return result;
  }
}
