package com.javafoundations.app.semana4.commandpattern;

public class AddItemCommand implements Command {

  private ItemManager item;
  private String name;

  public AddItemCommand(ItemManager item, String name) {
    this.item = item;
    this.name = name;
  }

  @Override
  public void execute() {
    item.createItem(name);
  }

  @Override
  public void undo() {
    item.deleteItem(name);
  }
}
