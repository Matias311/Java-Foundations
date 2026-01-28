package com.javafoundations.app.semana3.salesanalytics.exceptions;

public class FileArgumentException extends RuntimeException {
  public FileArgumentException(String message, Throwable e) {
    super(message, e);
  }

  public FileArgumentException(String message) {
    super(message);
  }
}
