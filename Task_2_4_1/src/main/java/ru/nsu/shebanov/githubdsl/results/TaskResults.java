package ru.nsu.shebanov.githubdsl.results;

/**
 * Task results.
 */
public class TaskResults {
    public String taskName;
    public boolean isPresent;
    public boolean buildSuccessfully;
    public double testScore;
    public boolean testResults;
    public boolean codeStyleResults;

    /**
     * Task results constructor.
     *
     * @param taskName task name
     */
    public TaskResults(String taskName) {
        this.taskName = taskName;
        this.buildSuccessfully = false;
        this.testScore = 0;
        this.testResults = false;
        this.codeStyleResults = false;
        this.isPresent = false;
    }

    /**
     * Construct string for task result.
     *
     * @return simple markdown output
     */
    @Override
    public String toString() {
        return String.format(
                "|'%s' b: %s, ts: %s, cs: %s|",
                taskName, buildSuccessfully, testResults, codeStyleResults);
    }
}
