package ru.nsu.shebanov.githubDSL;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import ru.nsu.shebanov.githubDSL.dsl.Course;
import ru.nsu.shebanov.githubDSL.dsl.Student;
import ru.nsu.shebanov.githubDSL.results.TaskResults;
import ru.nsu.shebanov.githubDSL.results.Result;
import ru.nsu.shebanov.githubDSL.results.UserResults;
import ru.nsu.shebanov.githubDSL.workers.Pull;

public class UserExecutor implements Runnable {
    private final List<String> foldersBeforeExecution;
    private final Student student;
    private final String downloadFolder;
    private final Course course;
    private final Result globalResult;

    public UserExecutor(
            List<String> foldersBeforeExecution,
            Student student,
            Course course,
            Result globalResult) {
        this.foldersBeforeExecution = foldersBeforeExecution;
        this.student = student;
        this.course = course;
        this.downloadFolder = course.downloadFolder;
        this.globalResult = globalResult;
    }

    @Override
    public void run() {
        Pull pull = new Pull(course, student, foldersBeforeExecution);
        try {
            pull.pull();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        String parentPath = downloadFolder + "/" + student.nickname;
        List<String> tasks;
        try {
            tasks = getFullPaths(parentPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Future<?>> taskThreads = new ArrayList<>();
        UserResults ur = new UserResults(course, this.student.name);

        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (var task : tasks) {
            String[] split = task.split("\\\\");
            String curTaskName = split[split.length - 1];
            boolean isPresent = false;
            for (var taskName : course.tasks) {
                isPresent = isPresent || taskName.name.equals(curTaskName);
            }
            if (!isPresent) {
                System.out.println("For task '" + curTaskName + "' nothing found");
                continue;
            }
            TaskResults tr = new TaskResults(curTaskName);
            ur.tr.add(tr);

            Future<?> future = executor.submit(new TaskExecutor(course, task, tr));
            taskThreads.add(future);
        }

        for (var future : taskThreads) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        executor.shutdown();
        ur.appendEmpty();
        this.globalResult.add(ur);
    }

    public static List<String> getFullPaths(String parentPath) throws IOException {
        try (var stream = Files.walk(Paths.get(parentPath), 1)) {
            return stream.filter(Files::isDirectory)
                    .filter(path -> !path.equals(Paths.get(parentPath)))
                    .map(Path::toString)
                    .collect(Collectors.toList());
        }
    }
}
