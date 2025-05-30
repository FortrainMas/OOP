package ru.nsu.shebanov.githubDomainSpecificLanguage.workers;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import ru.nsu.shebanov.githubDomainSpecificLanguage.results.TaskResults;

/**
 * Run the build task.
 */
public class Run {
    private final String taskPath;
    public TaskResults tr;
    private final int timeout;

    /**
     * Constructs the task.
     *
     * @param taskPath task path
     * @param tr task results
     */
    public Run(String taskPath, TaskResults tr) {
        this.taskPath = taskPath;
        this.tr = tr;
        this.timeout = 180;
    }

    /**
     * Run the task.
     *
     * @throws IOException actually throws
     * @throws InterruptedException actually throws
     */
    public void run() throws IOException, InterruptedException {
        String command = String.format("cd '%s'; ./gradlew build", taskPath);
        ProcessBuilder builder = new ProcessBuilder("powershell", "-Command", command);

        builder.redirectErrorStream(true);
        Process process = builder.start();

        boolean isFinished = process.waitFor(this.timeout, TimeUnit.SECONDS);
        if (!isFinished) {
            System.out.println(taskPath + " build failed (timeout)");
            return;
        }
        int exitCode = process.exitValue();

        if (exitCode == 0) {
            tr.buildSuccessfully = true;
            System.out.println(taskPath + " built successfully");
        } else {
            System.out.println(taskPath + " build failed");
        }
    }
}
