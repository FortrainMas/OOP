package ru.nsu.shebanov.githubdsl.dsl;

import java.util.List;

/**
 * Antlr course entity.
 */
public class Course {
    public List<Task> tasks;
    public List<Student> students;
    public List<Group> groups;
    public String downloadFolder;

    /**
     * Constructor for Antlr.
     *
     * @param tasks tasks
     * @param students students
     * @param groups groups
     * @param downloadFolder downloadFolder
     */
    public Course(
            List<Task> tasks, List<Student> students, List<Group> groups, String downloadFolder) {
        this.tasks = tasks;
        this.students = students;
        this.groups = groups;
        this.downloadFolder = downloadFolder;
    }
}
