package com.javafoundations.app.semana4.Commandpattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.javafoundations.app.semana4.commandpattern.AddItemCommand;
import com.javafoundations.app.semana4.commandpattern.Command;
import com.javafoundations.app.semana4.commandpattern.CommandHistory;
import com.javafoundations.app.semana4.commandpattern.Item;
import com.javafoundations.app.semana4.commandpattern.RemoveItemCommand;
import java.util.List;
import java.util.Stack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test for the disign patter command")
public class CommandHistoryTest {

  private CommandHistory control;
  private Item admin;

  @BeforeEach
  void setUp() {
    this.control = new CommandHistory();
    this.admin = new Item();
  }

  @Test
  @DisplayName("Test for the execute AddItem command execute")
  void executeAddItemCommandExecute() {
    control.execute(new AddItemCommand(admin, "Test"));
    List<String> list = admin.obtainItems();
    assertTrue(list.get(0).equals("Test"));
  }

  @Test
  @DisplayName("Test for the execute AddItem command undo")
  void executeAddItemCommandUndo() {
    control.execute(new AddItemCommand(admin, "Test"));
    control.undo();
    List<String> list = admin.obtainItems();
    Stack<Command> listHistory = control.getHistory();
    assertTrue(list.size() == 0);
    assertTrue(listHistory.size() == 0);
  }

  @Test
  @DisplayName("Test for the execute RemoveItem command execute")
  void executeRemoveCommandExecute() {
    control.execute(new AddItemCommand(admin, "Test"));
    control.execute(new RemoveItemCommand(admin, "Test"));
    List<String> list = admin.obtainItems();
    Stack<Command> listHistory = control.getHistory();
    assertTrue(list.size() == 0);
    assertTrue(listHistory.size() == 2);
  }

  @Test
  @DisplayName("Test for the execute RemoveItem command undo")
  void executeRemoveCommandUndo() {
    control.execute(new AddItemCommand(admin, "Test"));
    control.execute(new RemoveItemCommand(admin, "Test"));
    control.undo();
    List<String> list = admin.obtainItems();
    Stack<Command> listHistory = control.getHistory();
    assertTrue(list.size() == 1);
    assertTrue(listHistory.size() == 1);
  }
}
