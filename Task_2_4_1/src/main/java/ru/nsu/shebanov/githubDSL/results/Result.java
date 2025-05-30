package ru.nsu.shebanov.githubDSL.results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import ru.nsu.shebanov.githubDSL.dsl.Course;

/**
 * Result entity.
 */
public class Result {
    List<UserResults> userResults;
    private final Course course;

    /**
     * Result constructor.
     *
     * @param course course
     */
    public Result(Course course) {
        userResults = new ArrayList<>();
        this.course = course;
    }

    /**
     * Add new result.
     *
     * @param userResult user result
     */
    public synchronized void add(UserResults userResult) {
        userResults.add(userResult);
    }

    /**
     * Construct string for result.
     *
     * @return markdown output
     */
    @Override
    public String toString() {
        Set<String> taskNamesSet = new HashSet<>();

        for (var user : userResults) {
            for (var task : user.tr) {
                taskNamesSet.add(task.taskName);
            }
        }

        List<String> taskNames = new ArrayList<>(taskNamesSet);
        Collections.sort(taskNames);

        StringBuilder str = new StringBuilder("| User | ");
        for (var task : taskNames) {
            str.append(task).append("<br>(build/test/code style/points) |");
        }
        str.append(" Total |");
        str.append("\n|");
        str.append("-|".repeat(Math.max(0, taskNames.size() + 2)));
        str.append("\n");
        for (var user : userResults) {
            int userTotalScore = 0;
            str.append("|").append(String.format("%s", user.userName)).append("|");

            for (var task : course.tasks) {
                for (var userTask : user.tr) {
                    if (Objects.equals(userTask.taskName, task.name)) {
                        String build = userTask.buildSuccessfully ? "✅" : "❌";
                        String test = userTask.testResults ? "✅" : "❌";
                        String codeStyle = userTask.codeStyleResults ? "✅" : "❌";
                        str.append(
                                String.format(
                                        " (%s %s %s %d) |",
                                        build,
                                        test,
                                        codeStyle,
                                        userTask.buildSuccessfully && userTask.testResults
                                                ? task.maxPoints
                                                : 0));
                        userTotalScore +=
                                userTask.buildSuccessfully && userTask.testResults
                                        ? task.maxPoints
                                        : 0;
                    }
                }
            }
            str.append(String.format(" %d |\n", userTotalScore));
        }

        return str.toString();
    }
}
