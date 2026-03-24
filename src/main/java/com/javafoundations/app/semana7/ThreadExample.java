package com.javafoundations.app.semana7;

public class ThreadExample extends Thread {

  private String name;

  public ThreadExample(String name) {
    this.name = name;
  }

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      System.out.println(
          name
              + " thread start with the system name: "
              + Thread.currentThread().getName()
              + " and the number is: "
              + i);
    }

    try {
      Thread.sleep(2000);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void main(String[] args) {
    System.out.println("Testing how thread class works");
    ThreadExample exam1 = new ThreadExample("Test 1");
    ThreadExample exam2 = new ThreadExample("Test 2");
    exam1.start(); // we use start because execute the thread, run execute in line by line
    exam2.start();
    System.out.println("End");
  }
}
