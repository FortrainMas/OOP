package ru.nsu.shebanov.githubDSL;

import java.util.List;

public class Group {
    public String name;
    public List<String> students;

    public Group(String name, List<String> students) {
        this.name = name;
        this.students = students;
    }
}
