package com.javafoundations.app.semana8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

  private Socket clientSocket;

  public ClientHandler(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  @Override
  public void run() {
    System.out.println("Manage client on thread: " + Thread.currentThread().getName());

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {
      String line;

      while ((line = reader.readLine()) != null) {

        if ("exit".equalsIgnoreCase(line.trim())) {
          writer.println("Conexion close Bye!");
          break;
        }

        System.out.println("Message: " + line);

        // Echo
        writer.println("Echo: " + line);
      }

    } catch (IOException e) {
      System.out.println(e);
    } finally {
      try {
        clientSocket.close();
        System.out.println("Cliente disconected");
      } catch (IOException e) {
        System.out.println(e);
      }
    }

  }
}
