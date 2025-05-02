package ru.nsu.shebanov.githubDSL;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ExecutorDownload {
  private Course course;

  public ExecutorDownload(Course course) {
    this.course = course;
  }

  public void execute() throws IOException, InterruptedException {
    String downloadFolder = course.downloadFolder;
    List<String> folders = getSubfolders(downloadFolder);

    for (var folder : folders) {
      System.out.println(folder);
      String setDirectory = "cd " + downloadFolder + "/" + folder + "; ";
      String command = setDirectory + "git pull";
      ProcessBuilder pullCommand = new ProcessBuilder("powershell", command);

      pullCommand.inheritIO();
      Process process = pullCommand.start();
      process.waitFor();
    }

    for (var student : course.students) {
      if (!folders.contains(student.nickname)) {
        String setDirectory = "cd " + downloadFolder + "; ";
        String command =
            setDirectory
                + "git clone --branch main --single-branch "
                + student.repository
                + " "
                + student.nickname;
        ProcessBuilder cloneCommand = new ProcessBuilder("powershell", command);

        cloneCommand.inheritIO();
        Process process = cloneCommand.start();
        process.waitFor();
      }
    }
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
