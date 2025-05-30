package ru.nsu.shebanov.githubDomainSpecificLanguage;

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
import ru.nsu.shebanov.githubDomainSpecificLanguage.dsl.Course;
import ru.nsu.shebanov.githubDomainSpecificLanguage.dsl.Student;
import ru.nsu.shebanov.githubDomainSpecificLanguage.results.Result;
import ru.nsu.shebanov.githubDomainSpecificLanguage.results.TaskResults;
import ru.nsu.shebanov.githubDomainSpecificLanguage.results.UserResults;
import ru.nsu.shebanov.githubDomainSpecificLanguage.workers.Pull;

/**
 * Executor for user.
 */
public class UserExecutor implements Runnable {
    private final List<String> foldersBeforeExecution;
    private final Student student;
    private final String downloadFolder;
    private final Course course;
    private final Result globalResult;

    /**
     * Constructor for user executor.
     *
     * @param foldersBeforeExecution list of folders for users
     * @param student student
     * @param course course
     * @param globalResult global result
     */
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

    /**
     * Run the executor.
     */
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

    /**
     * Parse paths.
     *
     * @param parentPath parent folder
     * @return list of folders
     * @throws IOException throws exception
     */
    public static List<String> getFullPaths(String parentPath) throws IOException {
        try (var stream = Files.walk(Paths.get(parentPath), 1)) {
            return stream.filter(Files::isDirectory)
                    .filter(path -> !path.equals(Paths.get(parentPath)))
                    .map(Path::toString)
                    .collect(Collectors.toList());
        }
    }
}
