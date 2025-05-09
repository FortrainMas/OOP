package ru.nsu.shebanov.githubDSL;

import ru.nsu.shebanov.githubDSL.dsl.Course;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class Tester {
    private final Course course;

    public Tester(Course course) {
        this.course = course;
    }

    void startTesting() throws IOException, InterruptedException {
        String downloadFolder = course.downloadFolder;
        List<String> folders = getSubfolders(downloadFolder);

        List<Thread> studentThreads = new ArrayList<>();
        for(var student : course.students) {
            Runnable task = new UserExecutor(folders, student, course);
            Thread virtualThread = Thread.ofVirtual().unstarted(task);
            studentThreads.add(virtualThread);
            virtualThread.start();
        }

        for(var thread : studentThreads) {
            thread.join();
        }

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
