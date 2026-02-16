package com.javafoundations.app.semana4.StrategyPattern;

import java.util.List;

public class SortTasks {

  private SortStrategy<Task> strategy;

  public void setStrategy(SortStrategy<Task> strategy) {
    this.strategy = strategy;
  }

  public List<Task> sort(List<Task> list) {
    if (strategy != null) {
      return strategy.sort(list);
    } else {
      throw new IllegalStateException("Error: The strategy is null");
    }
  }
}
