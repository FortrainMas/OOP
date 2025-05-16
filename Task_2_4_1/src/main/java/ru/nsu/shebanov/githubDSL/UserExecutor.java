package ru.nsu.shebanov.githubDSL;

import ru.nsu.shebanov.githubDSL.dsl.Course;
import ru.nsu.shebanov.githubDSL.dsl.Student;
import ru.nsu.shebanov.githubDSL.results.Result;
import ru.nsu.shebanov.githubDSL.results.TaskResults;
import ru.nsu.shebanov.githubDSL.results.UserResults;
import ru.nsu.shebanov.githubDSL.workers.Pull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.*;

public class UserExecutor implements Runnable{
    private final List<String> foldersBeforeExecution;
    private final Student student;
    private final String downloadFolder;
    private final Course course;
    private final Result globalResult;

    public UserExecutor(List<String> foldersBeforeExecution, Student student, Course course, Result globalResult) {
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
            tasks = GetFullPaths(parentPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Thread> taskThreads = new ArrayList<>();
        UserResults ur = new UserResults(course, this.student.name);
        for(var task : tasks) {
            String[] split = task.split("\\\\");
            String cur_task_name = split[split.length - 1];
            boolean isPresent = false;
            for(var task_name : course.tasks) {
                isPresent = isPresent || task_name.name.equals(cur_task_name);
            }
            if (!isPresent) {
                System.out.println("For task '" + cur_task_name + "' nothing found");
                continue;
            }
            TaskResults tr = new TaskResults(cur_task_name);
            ur.tr.add(tr);

            Runnable taskThread = new TaskExecutor(course, task, tr);
            Thread virtualThread = Thread.ofVirtual().unstarted(taskThread);
            taskThreads.add(virtualThread);
            virtualThread.start();
        }

        for(var taskThread : taskThreads) {
            try {
                taskThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        ur.appendEmpty();
        for(var result : ur.tr) {
            System.out.println(result);
        }

    }

    public static List<String> GetFullPaths(String parentPath) throws IOException {
        try (var stream = Files.walk(Paths.get(parentPath), 1)) {
            return stream
                    .filter(Files::isDirectory)
                    .filter(path -> !path.equals(Paths.get(parentPath)))
                    .map(Path::toString)
                    .collect(Collectors.toList());
        }
    }
}
