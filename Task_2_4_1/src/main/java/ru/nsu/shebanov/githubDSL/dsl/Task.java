package ru.nsu.shebanov.githubDSL.dsl;

import java.time.LocalDate;

public class Task {
    public int id;
    public String name;
    public int maxPoints;
    public LocalDate softDeadline;
    public LocalDate hardDeadline;

    public Task(int id, String name, int maxPoints, LocalDate softDeadline, LocalDate hardDeadline) {
        this.id = id;
        this.name = name;
        this.maxPoints = maxPoints;
        this.softDeadline = softDeadline;
        this.hardDeadline = hardDeadline;
    }
}
