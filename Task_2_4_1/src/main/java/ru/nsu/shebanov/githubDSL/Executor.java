package ru.nsu.shebanov.githubDSL;

import java.io.IOException;

public class Executor {
  private final Course course;

  public Executor(Course course) {
    this.course = course;
  }

  public void execute() {
    try {
      ExecutorDownload ed = new ExecutorDownload(course);
      ed.execute();
      ExecuterRun er = new ExecuterRun(course);
      er.execute();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
      System.out.println("Execution of some command failed");
    }
  }
}
