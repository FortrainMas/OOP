package ru.nsu.shebanov.githubDSL.results;

import org.antlr.v4.runtime.misc.Array2DHashSet;
import ru.nsu.shebanov.githubDSL.dsl.Course;

import java.util.*;

public class Result {
    List<UserResults> userResults;
    private final Course course;

    public Result (Course course) {
        userResults = new ArrayList<>();
        this.course = course;
    }

    public synchronized void add(UserResults userResult) {
        userResults.add(userResult);
    }

    @Override
    public String toString() {
        Set<String> taskNamesSet = new HashSet<>();

        for (var user : userResults) {
            for (var task : user.tr) {
                taskNamesSet.add(task.task_name);
            }
        }

        List<String> taskNames = new ArrayList<>(taskNamesSet);
        Collections.sort(taskNames);

        StringBuilder str = new StringBuilder("| User | ");
        for(var task : taskNames) {
            str.append(task).append(" |");
        }
        str.append("\n|");
        str.append("-|".repeat(Math.max(0, taskNames.size() + 1)));
        str.append("\n");
        for(var user : userResults) {
            str.append("|").append(user.userName).append("|");

            for(var task : course.tasks) {
                for (var userTask : user.tr) {
                    if (Objects.equals(userTask.task_name, task.name)) {
                        int build = userTask.buildSuccessfully ? 1 : 0;
                        int test = userTask.testResults ? 1 : 0;
                        int codeStyle = userTask.codeStyleResults ? 1 : 0;

                        str.append(String.format(" (build: %d test: %d code style: %d points:%d) |",
                                build,
                                test,
                                codeStyle,
                                task.maxPoints * build * test
                        ));

                    }
                }
            }
            str.append("\n");
        }

        return str.toString();
    }
}
