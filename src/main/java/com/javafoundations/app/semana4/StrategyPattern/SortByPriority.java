package com.javafoundations.app.semana4.StrategyPattern;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SortByPriority implements SortStrategy<Task> {

  @Override
  public List<Task> sort(List<Task> list) {
    Objects.requireNonNull(list);
    return list.stream()
        .sorted(Comparator.comparing(Task::priority).thenComparing(Task::startDate).reversed())
        .toList();
  }
}
