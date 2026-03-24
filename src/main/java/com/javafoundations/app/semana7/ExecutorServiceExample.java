package com.javafoundations.app.semana7;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceExample {

  private static void task() {
    System.out.println("executing the thread with the name: " + Thread.currentThread().getName());
    try {
      Thread.sleep(2000);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private static void threadPoolWithFixed() throws InterruptedException {
    System.out.println("Executing with a pool of threads (2 threads on the pool)");
    ExecutorService pool = Executors.newFixedThreadPool(2); // two thread
    pool.execute(() -> task());
    pool.execute(() -> task());
    pool.execute(() -> task());
    pool.execute(() -> task());

    pool.shutdown();
    if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
      System.out.println("Couldnt finish the tasks");
      pool.shutdownNow();
    }
  }

  private static void newSingleThreadExecutor() throws InterruptedException, ExecutionException {
    System.out.println("Executing a thread with a single thread executor");
    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.execute(() -> task());

    System.out.println("Mini Calculator");
    Future<Integer> result =
        executor.submit(() -> new FutureWithCallableExample("sum", 2, 2).call());
    System.out.println("Result of the operation is: " + result.get());
    System.out.println("Finish");

    executor.shutdown();
    if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {
      System.out.println("Couldnt finish the tasks");
      executor.shutdownNow();
    }
  }

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    threadPoolWithFixed();
    newSingleThreadExecutor();
  }
}
