package com.javafoundations.app.semana4.ObserverPattern;

import java.util.HashMap;
import java.util.Map;

public class MetricsListener implements NotifyObserver {

  private Map<String, Integer> metricsLogger = new HashMap<>();

  @Override
  public void onEvent(String n) {
    if (n == null || n.isEmpty()) {
      throw new IllegalArgumentException("Error: the event must have a value");
    }
    if (metricsLogger.size() >= 1) {
      metricsLogger.put(n, (metricsLogger.get(n) + 1));
    } else {
      metricsLogger.put(n, 1);
    }
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

  public Map<String, Integer> getMetricsLogger() {
    return metricsLogger;
  }
}
