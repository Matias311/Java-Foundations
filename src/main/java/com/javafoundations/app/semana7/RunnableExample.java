package com.javafoundations.app.semana7;

public class RunnableExample implements Runnable {

  private String name;

  public RunnableExample(String name) {
    this.name = name;
  }

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      System.out.println(
          name
              + " executing the thread with the name: "
              + Thread.currentThread().getName()
              + " and the i proces: "
              + i);
    }
    try {
      Thread.sleep(2000);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void main(String[] args) {
    Thread ex1 = new Thread(new RunnableExample("Run 1"));
    Thread ex2 = new Thread(new RunnableExample("Run 2"));
    ex1.start();
    ex2.start();
    System.out.println("End");
  }
}
