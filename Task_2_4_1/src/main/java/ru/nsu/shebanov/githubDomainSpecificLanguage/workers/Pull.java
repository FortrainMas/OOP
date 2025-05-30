package ru.nsu.shebanov.githubDomainSpecificLanguage.workers;

import java.io.IOException;
import java.util.List;
import ru.nsu.shebanov.githubDomainSpecificLanguage.dsl.Course;
import ru.nsu.shebanov.githubDomainSpecificLanguage.dsl.Student;

/**
 * Pull tasks for user.
 */
public class Pull {
    private final Student student;
    private final List<String> foldersBeforeExecution;
    private final String downloadFolder;

    /**
     * Constructs task.
     *
     * @param course course
     * @param student student
     * @param foldersBeforeExecution folder of user
     */
    public Pull(Course course, Student student, List<String> foldersBeforeExecution) {
        this.student = student;
        this.foldersBeforeExecution = foldersBeforeExecution;
        this.downloadFolder = course.downloadFolder;
    }

    /**
     * Pull task.
     *
     * @throws IOException actually throws
     * @throws InterruptedException actually throws
     */
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
