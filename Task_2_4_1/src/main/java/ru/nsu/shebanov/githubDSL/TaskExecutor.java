package ru.nsu.shebanov.githubDSL;

import java.io.IOException;
import ru.nsu.shebanov.githubDSL.dsl.Course;
import ru.nsu.shebanov.githubDSL.results.TaskResults;
import ru.nsu.shebanov.githubDSL.workers.CheckStyle;
import ru.nsu.shebanov.githubDSL.workers.Run;
import ru.nsu.shebanov.githubDSL.workers.Test;

/**
 * Worker for tasks.
 */
public class TaskExecutor implements Runnable {
    private final String taskPath;
    public Course course;
    public TaskResults tr;

    /**
     * Executor of the tasks.
     *
     * @param course course
     * @param taskPath task path
     * @param tr task results
     */
    public TaskExecutor(Course course, String taskPath, TaskResults tr) {
        this.taskPath = taskPath;
        this.course = course;
        this.tr = tr;
    }

    /**
     * Actually runs the task.
     */
    @Override
    public void run() {
        Run run = new Run(taskPath, tr);
        Test test = new Test(taskPath, tr);
        CheckStyle style = new CheckStyle(course, tr, taskPath);
        try {
            run.run();
            if (!tr.buildSuccessfully) {
                return;
            }
            test.run();
            if (!tr.testResults) {
                return;
            }
            style.run();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
