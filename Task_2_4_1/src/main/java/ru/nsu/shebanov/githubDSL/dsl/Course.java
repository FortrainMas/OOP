package ru.nsu.shebanov.githubDSL.dsl;

import java.util.List;

public class Course {
    public List<Task> tasks;
    public List<Student> students;
    public List<Group> groups;
    public String downloadFolder;

    public Course(
            List<Task> tasks, List<Student> students, List<Group> groups, String downloadFolder) {
        this.tasks = tasks;
        this.students = students;
        this.groups = groups;
        this.downloadFolder = downloadFolder;
    }
}
