package com.javafoundations.app.semana4.ObserverPattern;

import java.util.ArrayList;
import java.util.List;

public class AuditLogListener implements NotifyObserver {

  private List<String> loggerInMemory = new ArrayList<>();

  @Override
  public void onEvent(String n) {
    if (n == null || n.isEmpty()) {
      throw new IllegalArgumentException("Error: the event must have a value");
    }
    loggerInMemory.add(n);
  }

  @Override
  public int hashCode() {
    return this.getClass().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    return this.getClass() == obj.getClass();
  }

  public List<String> getLoggerInMemory() {
    return loggerInMemory;
  }
}
