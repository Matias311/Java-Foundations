package com.javafoundations.app.semana4.commandpattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Deque;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test for the disign patter command")
public class CommandHistoryTest {

  private CommandHistory control;
  private ItemManager manager;

  @BeforeEach
  void setUp() {
    this.control = new CommandHistory();
    this.manager = new ItemManager();
  }

  @Test
  @DisplayName("Test for the execute AddItem command execute")
  void executeAddItemCommandExecute() {
    control.execute(new AddItemCommand(manager, "Test"));
    List<String> list = manager.obtainItems();
    assertEquals("Test", list.get(0));
  }

  @Test
  @DisplayName("Test for the execute AddItem command undo")
  void executeAddItemCommandUndo() {
    control.execute(new AddItemCommand(manager, "Test"));
    assertTrue(control.undo());
    List<String> list = manager.obtainItems();
    Deque<Command> listHistory = control.getHistory();
    assertEquals(0, list.size());
    assertEquals(0, listHistory.size());
  }

  @Test
  @DisplayName("Test for the execute RemoveItem command execute")
  void executeRemoveCommandExecute() {
    control.execute(new AddItemCommand(manager, "Test"));
    control.execute(new RemoveItemCommand(manager, "Test"));
    List<String> list = manager.obtainItems();
    Deque<Command> listHistory = control.getHistory();

    assertEquals(0, list.size());
    assertEquals(2, listHistory.size());
  }

  @Test
  @DisplayName("Test for the execute RemoveItem command undo")
  void executeRemoveCommandUndo() {
    control.execute(new AddItemCommand(manager, "Test"));
    control.execute(new RemoveItemCommand(manager, "Test"));
    assertTrue(control.undo());
    assertEquals("Test", manager.obtainItems().get(0));
    Deque<Command> listHistory = control.getHistory();
    assertEquals(1, listHistory.size());
  }

  @Test
  @DisplayName("Test the order from the history")
  void orderHistory() {
    control.execute(new AddItemCommand(manager, "Test"));
    control.execute(new AddItemCommand(manager, "Test2"));
    control.undo();
    assertEquals("Test", manager.obtainItems().get(0));
  }
}
