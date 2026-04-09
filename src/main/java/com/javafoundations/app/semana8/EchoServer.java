package com.javafoundations.app.semana8;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {

  private static final int PORT = 3103;
  private static final int POOL_MAX_SIZE = 100;

  public static void main(String[] args) {
    ExecutorService pool = Executors.newFixedThreadPool(POOL_MAX_SIZE);

    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      System.out.println("Server listing port " + PORT);

      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("New client " + clientSocket.getRemoteSocketAddress());
        pool.execute(new ClientHandler(clientSocket));
      }

    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
