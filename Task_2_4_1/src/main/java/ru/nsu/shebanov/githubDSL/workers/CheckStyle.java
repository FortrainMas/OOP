package ru.nsu.shebanov.githubDSL.workers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import ru.nsu.shebanov.githubDSL.dsl.Course;
import ru.nsu.shebanov.githubDSL.results.TaskResults;


/**
 * Checkstyle task.
 */
public class CheckStyle {
    public String checkstylePath = "D:/programm_files/javadog/checkstyle.jar";
    public String configPath = "D:/programm_files/javadog/google_checks.xml";

    public Course course;
    public String taskPath;
    public TaskResults tr;

    private final int timeout;

    /**
     * Constructs check style task.
     *
     * @param course course
     * @param tr task results
     * @param taskPath path to the task
     */
    public CheckStyle(Course course, TaskResults tr, String taskPath) {
        this.course = course;
        this.taskPath = taskPath;
        this.tr = tr;

        this.timeout = 120;
    }

    /**
     * Run the check style.
     *
     * @throws IOException actually throws
     * @throws InterruptedException actually throws
     */
    public void run() throws IOException, InterruptedException {
        String command =
                String.format(
                        "cd '%s'; java -jar %s -c %s src/", taskPath, checkstylePath, configPath);
        ProcessBuilder builder = new ProcessBuilder("powershell", "-Command", command);

        builder.redirectErrorStream(true); // объединяет stderr и stdout
        Process process = builder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        boolean hasWarn = false;

        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
            if (line.contains("[WARN]")) {
                hasWarn = true;
            }
        }

        boolean isFinished = process.waitFor(this.timeout, TimeUnit.SECONDS);
        if (!isFinished) {
            tr.codeStyleResults = false;
            System.out.println(taskPath + "checkstyle timeout");
        }

        int exitCode = process.exitValue();

        if (exitCode == 0) {
            if (hasWarn) {
                System.out.println(taskPath + " has warnings:");
                System.out.println(output);
            } else {
                tr.codeStyleResults = true;
                System.out.println(taskPath + " OK — no warnings.");
            }
        } else {
            System.out.println(taskPath + " FAILED (exit code " + exitCode + ")");
            System.out.println(output);
        }
    }
}
