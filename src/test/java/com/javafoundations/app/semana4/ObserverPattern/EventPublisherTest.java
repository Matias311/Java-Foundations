package com.javafoundations.app.semana4.ObserverPattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

@DisplayName("Class to test the Observer design pattern")
public class EventPublisherTest {
  private EventPublisher publisher;

  @BeforeEach
  void setUp() {
    this.publisher = new EventPublisher();
  }

  @Test
  @DisplayName("Subscribe a listener to the publisher")
  void subscribeListener() {
    assertTrue(publisher.subscribe(new MetricsListener()));
  }

  @Test
  @DisplayName(
      "Subscribe two listener (Metrict) that are the same, should not be added the second one")
  void shouldReturnFalseWhenWeAddSecondOneMetricListener() {
    publisher.subscribe(new MetricsListener());
    assertFalse(publisher.subscribe(new MetricsListener()));
    assertEquals(1, publisher.getListeners().size());
  }

  @Test
  @DisplayName(
      "Subscribe two listener (AuditLogListener) that are the same, should not be added the second"
          + " one")
  void shouldReturnFalseWhenWeAddSecondOneAuditLogListener() {
    publisher.subscribe(new AuditLogListener());
    assertFalse(publisher.subscribe(new AuditLogListener()));
    assertEquals(1, publisher.getListeners().size());
  }

  @Test
  @DisplayName("Test the publish method with a logaudit listener")
  void logAuditListenerPublish() {
    AuditLogListener audit = new AuditLogListener();
    publisher.subscribe(audit);
    publisher.publish("Audit Test");
    assertEquals("Audit Test", audit.getLoggerInMemory().get(0));
  }

  @Test
  @DisplayName("Test the publish method with a metrics listener")
  void metricsListenerPublish() {
    MetricsListener metrics = new MetricsListener();
    publisher.subscribe(metrics);
    publisher.publish("Metrics Test");
    assertEquals(1, metrics.getMetricsLogger().get("Metrics Test"));
    assertEquals("[Metrics Test]", metrics.getMetricsLogger().keySet().toString());
  }

  @ParameterizedTest
  @EmptySource
  @DisplayName("Empty event using MetricsListener")
  void emptyEventUsingMetricsListener(String empty) {
    publisher.subscribe(new MetricsListener());
    Exception ex = assertThrows(IllegalArgumentException.class, () -> publisher.publish(empty));
    assertEquals("Error: the event must have a value", ex.getMessage());
  }

  @ParameterizedTest
  @NullSource
  @DisplayName("Null event using MetricsListener")
  void nullEventUsingMetricsListener(String nullS) {
    publisher.subscribe(new MetricsListener());
    Exception ex = assertThrows(IllegalArgumentException.class, () -> publisher.publish(nullS));
    assertEquals("Error: the event must have a value", ex.getMessage());
  }

  @ParameterizedTest
  @EmptySource
  @DisplayName("Empty event using AuditLog")
  void emptyEventUsingAuditLogListener(String empty) {
    publisher.subscribe(new AuditLogListener());
    Exception ex = assertThrows(IllegalArgumentException.class, () -> publisher.publish(empty));
    assertEquals("Error: the event must have a value", ex.getMessage());
  }

  @ParameterizedTest
  @NullSource
  @DisplayName("Null event using MetricsListener")
  void nullEventUsingAuditLogListener(String nullS) {
    publisher.subscribe(new AuditLogListener());
    Exception ex = assertThrows(IllegalArgumentException.class, () -> publisher.publish(nullS));
    assertEquals("Error: the event must have a value", ex.getMessage());
  }
}
