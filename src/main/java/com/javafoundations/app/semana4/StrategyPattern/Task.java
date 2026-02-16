package com.javafoundations.app.semana4.StrategyPattern;

import java.time.LocalDate;

public record Task(int id, String name, LocalDate startDate, LocalDate dueDate, int priority) {}

// 1 -> priority low
// 2 -> priority medimum
// 3 -> priority high
