package com.javafoundations.app.semana4.commandpattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item {

  private List<String> listItem = new ArrayList<>();

  public void createItem(String s) {
    Objects.requireNonNull(s);
    listItem.add(s);
  }

  public void deleteItem(String s) {
    Objects.requireNonNull(s);
    listItem.remove(s);
  }

  public List<String> obtainItems() {
    return listItem;
  }
}
