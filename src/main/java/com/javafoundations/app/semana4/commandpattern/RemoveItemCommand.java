package com.javafoundations.app.semana4.commandpattern;

public class RemoveItemCommand implements Command {

  private ItemManager item;
  private String name;

  public RemoveItemCommand(ItemManager item, String name) {
    this.item = item;
    this.name = name;
  }

  @Override
  public void execute() {
    item.deleteItem(name);
  }

  @Override
  public void undo() {
    item.createItem(name);
  }
}
