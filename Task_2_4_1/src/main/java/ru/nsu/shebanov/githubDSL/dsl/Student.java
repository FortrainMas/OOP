package ru.nsu.shebanov.githubDSL.dsl;

public class Student {
    public String nickname;
    public String name;
    public String repository;

    public Student(String nickname, String name, String repository) {
        this.nickname = nickname;
        this.name = name;
        this.repository = repository;
    }

    @Override
    public String toString() {
        return nickname + " " + name + " " + repository;
    }
}
