package com.javafoundations.app.semana4.StrategyPattern;

import java.util.List;

public interface SortStrategy<T> {

  public List<T> sort(List<T> list);
}
