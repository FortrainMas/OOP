package ru.nsu.shebanov.githubDSL;

import ru.nsu.shebanov.githubDSL.dsl.Course;
import ru.nsu.shebanov.githubDSL.results.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;



public class Tester {
    private final Course course;

    public Tester(Course course) {
        this.course = course;
    }

    void startTesting() throws IOException, InterruptedException, ExecutionException {
        String downloadFolder = course.downloadFolder;
        List<String> folders = getSubfolders(downloadFolder);

        List<Future<?>> studentThreads = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Result globalResult = new Result();
        for(var student : course.students) {
            Future<?> future = executor.submit(new UserExecutor(folders, student, course, globalResult));
            studentThreads.add(future);
        }

        for(var thread : studentThreads) {
            thread.get();
        }

        System.out.println(globalResult);
        System.out.println("Finished testing");
    }

    private static List<String> getSubfolders(String folderPath) throws IOException {
    try (var paths = Files.list(Paths.get(folderPath))) {
      return paths
          .filter(Files::isDirectory)
          .map(path -> path.getFileName().toString())
          .collect(Collectors.toList());
        }
    }
}
