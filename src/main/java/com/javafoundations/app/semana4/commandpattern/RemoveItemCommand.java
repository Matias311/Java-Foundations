package com.javafoundations.app.semana4.commandpattern;

public class RemoveItemCommand implements Command {

  private Item item;
  private String s;

  public RemoveItemCommand(Item item, String s) {
    this.item = item;
    this.s = s;
  }

  @Override
  public void execute() {
    item.deleteItem(s);
  }

  @Override
  public void undo() {
    item.createItem(s);
  }
}
