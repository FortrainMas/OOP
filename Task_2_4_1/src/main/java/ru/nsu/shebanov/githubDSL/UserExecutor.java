package ru.nsu.shebanov.githubDSL;

import ru.nsu.shebanov.githubDSL.dsl.Course;
import ru.nsu.shebanov.githubDSL.dsl.Student;
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

    public UserExecutor(List<String> foldersBeforeExecution, Student student, Course course) {
        this.foldersBeforeExecution = foldersBeforeExecution;
        this.student = student;
        this.course = course;
        this.downloadFolder = course.downloadFolder;
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
        for(var task : tasks) {
            Runnable taskThread = new TaskExecutor(task);
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
