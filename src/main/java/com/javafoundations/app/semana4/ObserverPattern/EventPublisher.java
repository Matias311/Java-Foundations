package com.javafoundations.app.semana4.ObserverPattern;

import java.util.LinkedHashSet;
import java.util.Set;

public class EventPublisher {

  private Set<NotifyObserver> listeners = new LinkedHashSet<>();

  public boolean subscribe(NotifyObserver listener) {
    return listeners.add(listener);
  }

  public Set<NotifyObserver> getListeners() {
    return listeners;
  }

  public void publish(String event) {
    listeners.forEach(l -> l.onEvent(event));
  }
}
