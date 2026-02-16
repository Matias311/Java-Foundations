package com.javafoundations.app.semana4.StrategyPattern;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test for the strategy design pattern sorting tasks by the date and priority (1 to 3)")
public class SortStrategyTest {

  private List<Task> list;
  private SortTasks sortTasks;

  @BeforeEach
  void setUp() {
    this.list =
        new ArrayList<>(
            List.of(
                new Task(1, "Code a API", LocalDate.now(), LocalDate.now().plusDays(1), 3),
                new Task(
                    1,
                    "Do the test for the service",
                    LocalDate.now(),
                    LocalDate.now().plusDays(2),
                    2),
                new Task(
                    1, "Do the documentation", LocalDate.now(), LocalDate.now().plusDays(3), 3)));
    this.sortTasks = new SortTasks();
  }

  @Test
  @DisplayName(
      "Sort the task by the due date, should be 1. Do the documentation,2. Do the test,3. Code API")
  void shouldReturnAnewTaskListSortedByTheDueDate() {
    sortTasks.setStrategy(new SortByDueDate());
    List<Task> sortListByDueDate = sortTasks.sort(list);
    assertAll(
        () -> assertEquals(3, sortListByDueDate.size()),
        () -> assertEquals("Do the documentation", sortListByDueDate.get(0).name()),
        () -> assertEquals("Do the test for the service", sortListByDueDate.get(1).name()),
        () -> assertEquals("Code a API", sortListByDueDate.get(2).name()));
  }

  @Test
  @DisplayName(
      "Sort by the priority or due date (if more than one have the same priority) 1. Code a API, 2."
          + " DO the documentation, 3. Do the test for the service")
  void sortByPriorityOrDueDateListTask() {
    sortTasks.setStrategy(new SortByPriority());
    List<Task> sortPriority = sortTasks.sort(list);
    assertAll(
        () -> assertEquals(3, sortPriority.size()),
        () -> assertEquals("Code a API", sortPriority.get(0).name()),
        () -> assertEquals("Do the documentation", sortPriority.get(1).name()),
        () -> assertEquals("Do the test for the service", sortPriority.get(2).name()));
  }

  @Test
  @DisplayName("Null Strategy should throw IllegalStateException")
  void nullStategyShouldThrowIllegalStateException() {
    Exception ex = assertThrows(IllegalStateException.class, () -> sortTasks.sort(list));
    assertEquals("Error: The strategy is null", ex.getMessage());
  }
}
