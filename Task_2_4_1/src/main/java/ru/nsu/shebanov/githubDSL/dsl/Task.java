package ru.nsu.shebanov.githubDSL.dsl;

import java.time.LocalDate;

/**
 * Antlr entity for task.
 */
public class Task {
    public int id;
    public String name;
    public int maxPoints;
    public LocalDate softDeadline;
    public LocalDate hardDeadline;

    /**
     * Task constructor.
     *
     * @param id task id
     * @param name task name
     * @param maxPoints task max points
     * @param softDeadline task soft deadline
     * @param hardDeadline task hard deadline
     */
    public Task(
            int id, String name, int maxPoints, LocalDate softDeadline, LocalDate hardDeadline) {
        this.id = id;
        this.name = name;
        this.maxPoints = maxPoints;
        this.softDeadline = softDeadline;
        this.hardDeadline = hardDeadline;
    }
}
