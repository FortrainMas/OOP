package ru.nsu.shebanov.githubDSL;

import ru.nsu.shebanov.githubDSL.workers.Run;
import ru.nsu.shebanov.githubDSL.workers.Test;

import java.io.IOException;

public class TaskExecutor implements Runnable{
    private final String taskPath;

    public TaskExecutor(String taskPath) {
        this.taskPath = taskPath;
    }

    @Override
    public void run() {
        Run run = new Run(taskPath);
        Test test = new Test(taskPath);
        try {
            run.run();
            test.run();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
