package ru.nsu.shebanov.githubDSL.results;

import org.antlr.v4.runtime.misc.Array2DHashSet;

import java.util.*;

public class Result {
    List<UserResults> userResults;

    public Result () {
        userResults = new ArrayList<>();
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

            for(var task : taskNames) {
                for (var userTask : user.tr) {
                    if (Objects.equals(userTask.task_name, task)) {
                        str.append(String.format(" (build: %d test: %d code style: %d) |",
                                userTask.buildSuccessfully ? 1 : 0,
                                userTask.testResults ? 1 : 0,
                                userTask.codeStyleResults ? 1 : 0
                        ));
                    }
                }
            }
            str.append("\n");
        }

        return str.toString();
    }
}
