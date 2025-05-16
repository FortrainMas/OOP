package ru.nsu.shebanov.githubDSL.results;

import java.util.ArrayList;
import java.util.List;

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
        StringBuilder res = new StringBuilder();
        for(var result : userResults) {
            res.append(result.toString()).append("\n");
        }
        return res.toString();
    }
}
