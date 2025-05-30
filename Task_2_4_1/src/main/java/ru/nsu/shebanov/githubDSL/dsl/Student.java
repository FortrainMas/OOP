package ru.nsu.shebanov.githubDSL.dsl;

/**
 * Antlr student entity.
 */
public class Student {
    public String nickname;
    public String name;
    public String repository;

    /**
     * Student constructor.
     *
     * @param nickname nickname of the student
     * @param name name of the student
     * @param repository student's repository
     */
    public Student(String nickname, String name, String repository) {
        this.nickname = nickname;
        this.name = name;
        this.repository = repository;
    }

    /**
     * Constructor string from student.
     *
     * @return student's string
     */
    @Override
    public String toString() {
        return nickname + " " + name + " " + repository;
    }
}
