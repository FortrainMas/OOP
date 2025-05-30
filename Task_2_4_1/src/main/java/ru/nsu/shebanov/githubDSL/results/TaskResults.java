package ru.nsu.shebanov.githubDSL.results;

public class TaskResults {
    public String task_name;
    public boolean isPresent;
    public boolean buildSuccessfully;
    public double testScore;
    public boolean testResults;
    public boolean codeStyleResults;

    public TaskResults(String task_name) {
        this.task_name = task_name;
        this.buildSuccessfully = false;
        this.testScore = 0;
        this.testResults = false;
        this.codeStyleResults = false;
        this.isPresent = false;
    }

    @Override
    public String toString() {
        return String.format(
                "|'%s' b: %s, ts: %s, cs: %s|",
                task_name, buildSuccessfully, testResults, codeStyleResults);
    }
}
