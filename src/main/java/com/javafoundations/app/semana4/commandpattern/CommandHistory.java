package com.javafoundations.app.semana4.commandpattern;

import java.util.ArrayDeque;
import java.util.Deque;

public class CommandHistory {

  private Deque<Command> commandHistory = new ArrayDeque<>();

  public void execute(Command command) {
    command.execute();
    commandHistory.push(command);
  }

  public boolean undo() {
    if (!commandHistory.isEmpty()) {
      Command ultimo = commandHistory.pop();
      ultimo.undo();
      return true;
    } else {
      return false;
    }
  }

  public Deque<Command> getHistory() {
    return new ArrayDeque<>(commandHistory);
  }
}
