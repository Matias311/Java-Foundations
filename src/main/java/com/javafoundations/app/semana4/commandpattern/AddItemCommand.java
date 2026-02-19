package com.javafoundations.app.semana4.commandpattern;

public class AddItemCommand implements Command {

  private Item item;
  private String s;

  public AddItemCommand(Item item, String s) {
    this.item = item;
    this.s = s;
  }

  @Override
  public void execute() {
    item.createItem(s);
  }

  @Override
  public void undo() {
    item.deleteItem(s);
  }
}
