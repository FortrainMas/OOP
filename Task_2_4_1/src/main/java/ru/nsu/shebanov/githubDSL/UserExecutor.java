package ru.nsu.shebanov.githubDSL;

import ru.nsu.shebanov.githubDSL.workers.Pull;

import java.io.IOException;
import java.util.List;

public class UserExecutor implements Runnable{
    private final List<String> foldersBeforeExecution;
    private final Student student;
    private final String downloadFolder;
    private final Course course;

    public UserExecutor(List<String> foldersBeforeExecution, Student student, Course course) {
        this.foldersBeforeExecution = foldersBeforeExecution;
        this.student = student;
        this.course = course;
        this.downloadFolder = course.downloadFolder;
    }


    @Override
    public void run() {
        Pull pull = new Pull(course, student, foldersBeforeExecution);
        try {
            pull.pull();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
