package com.javafoundations.app.semana4.commandpattern;

import java.util.Stack;

public class CommandHistory {

  private Stack<Command> commandHistory = new Stack<>();

  public void execute(Command command) {
    command.execute();
    commandHistory.add(command);
  }

  public void undo() {
    if (!commandHistory.isEmpty()) {
      Command ultimo = commandHistory.pop();
      ultimo.undo();
      System.out.println("Command undo executed");
    } else {
      System.out.println("Can not be execute");
    }
  }

  public Stack<Command> getHistory() {
    return commandHistory;
  }
}
