package ru.nsu.shebanov.githubdsl.dsl;

import java.util.List;

/**
 * Antlr entity for Group.
 */
public class Group {
    public String name;
    public List<String> students;

    /**
     * Group constructor for them.
     *
     * @param name name
     * @param students students list
     */
    public Group(String name, List<String> students) {
        this.name = name;
        this.students = students;
    }
}
