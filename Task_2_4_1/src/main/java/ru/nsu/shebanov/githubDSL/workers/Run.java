package ru.nsu.shebanov.githubDSL.workers;

import ru.nsu.shebanov.githubDSL.results.TaskResults;

import java.io.IOException;

public class Run {
    private final String taskPath;
    public TaskResults tr;

    public Run(String taskPath, TaskResults tr) {
        this.taskPath = taskPath;
        this.tr = tr;
    }

    public void run() throws IOException, InterruptedException {
        String command = String.format("cd '%s'; ./gradlew build", taskPath);
        ProcessBuilder builder = new ProcessBuilder("powershell", "-Command", command);

        builder.redirectErrorStream(true);
        Process process = builder.start();
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            tr.buildSuccessfully = true;
            System.out.println(taskPath + " built successfully");
        } else {
            System.out.println(taskPath + " build failed");
        }
    }


}
