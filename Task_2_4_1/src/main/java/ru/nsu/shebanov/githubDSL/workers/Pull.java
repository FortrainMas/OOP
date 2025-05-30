package ru.nsu.shebanov.githubDSL.workers;

import java.io.IOException;
import java.util.List;
import ru.nsu.shebanov.githubDSL.dsl.Course;
import ru.nsu.shebanov.githubDSL.dsl.Student;

public class Pull {
    private final Student student;
    private final List<String> foldersBeforeExecution;
    private final String downloadFolder;

    public Pull(Course course, Student student, List<String> foldersBeforeExecution) {
        this.student = student;
        this.foldersBeforeExecution = foldersBeforeExecution;
        this.downloadFolder = course.downloadFolder;
    }

    public void pull() throws IOException, InterruptedException {
        if (foldersBeforeExecution.contains(student.nickname)) {
            String setDirectory = "cd " + downloadFolder + "/" + student.nickname + "; ";
            String command = setDirectory + "git pull";
            ProcessBuilder pullCommand = new ProcessBuilder("powershell", command);

            pullCommand.inheritIO();
            Process process = pullCommand.start();
            process.waitFor();
        } else {
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
