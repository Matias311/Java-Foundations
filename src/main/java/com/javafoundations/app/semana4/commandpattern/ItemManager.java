package com.javafoundations.app.semana4.commandpattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ItemManager {

  private List<String> listItem = new ArrayList<>();

  public void createItem(String name) {
    Objects.requireNonNull(name);
    listItem.add(name);
  }

  public void deleteItem(String name) {
    Objects.requireNonNull(name);
    listItem.remove(name);
  }

  public List<String> obtainItems() {
    return Collections.unmodifiableList(listItem);
  }
}
