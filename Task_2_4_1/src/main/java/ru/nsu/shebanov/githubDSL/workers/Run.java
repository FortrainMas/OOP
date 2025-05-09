package ru.nsu.shebanov.githubDSL.workers;

import java.io.IOException;

public class Run {
    private final String taskPath;

    public Run(String taskPath) {
        this.taskPath = taskPath;
    }

    public void run() throws IOException, InterruptedException {
        String command = String.format("cd '%s'; ./gradlew build", taskPath);
        ProcessBuilder builder = new ProcessBuilder("powershell", "-Command", command);

        builder.redirectErrorStream(true);
        Process process = builder.start();
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            System.out.println(taskPath + " ok");
        } else {
            System.out.println(taskPath + " failed");
        }
    }


}
