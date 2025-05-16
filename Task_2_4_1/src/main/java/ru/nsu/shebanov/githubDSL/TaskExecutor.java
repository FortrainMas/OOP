package ru.nsu.shebanov.githubDSL;

import ru.nsu.shebanov.githubDSL.dsl.Task;
import ru.nsu.shebanov.githubDSL.results.TaskResults;
import ru.nsu.shebanov.githubDSL.workers.CheckStyle;
import ru.nsu.shebanov.githubDSL.workers.Run;
import ru.nsu.shebanov.githubDSL.workers.Test;
import ru.nsu.shebanov.githubDSL.dsl.Course;

import java.io.IOException;

public class TaskExecutor implements Runnable{
    private final String taskPath;
    public Course course;
    public TaskResults tr;

    public TaskExecutor(Course course, String taskPath, TaskResults tr) {
        this.taskPath = taskPath;
        this.course = course;
        this.tr = tr;
    }

    @Override
    public void run() {
        Run run = new Run(taskPath, tr);
        Test test = new Test(taskPath, tr);
        CheckStyle style = new CheckStyle(course, tr, taskPath);
        try {
            run.run();
            if(!tr.buildSuccessfully) return;;
            test.run();
            if(!tr.testResults) return;
            style.run();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
