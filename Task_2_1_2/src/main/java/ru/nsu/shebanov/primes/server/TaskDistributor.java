package ru.nsu.shebanov.primes.server;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.shebanov.primes.common.KnownPrimes;
import ru.nsu.shebanov.primes.common.PrimesDelta;
import ru.nsu.shebanov.primes.common.Submission;
import ru.nsu.shebanov.primes.common.Task;

/**
 * Factory for tasks.
 */
public class TaskDistributor {
    public final KnownPrimes knownPrimes;

    public int minimum;
    public int maximum;
    public int numWorkers;

    public int maxUndistributed;
    public List<Submission> submissions;

    /**
     * Constructor.
     *
     * @param knownPrimes mutable list of known primes
     */
    TaskDistributor(KnownPrimes knownPrimes) {
        this.knownPrimes = knownPrimes;
        this.maximum = 0;

        this.numWorkers = 1;
        setUpTask();
    }

    /**
     * Configures new task.
     */
    private void setUpTask() {
        this.maxUndistributed = 0;
        submissions = new ArrayList<>();
        for (int i = 0; i < numWorkers * 2; i++) {
            submissions.add(null);
        }

        if (this.maximum == 0) {
            this.minimum = 2;
            this.maximum = 1000;
        } else {
            this.minimum = this.maximum;
            this.maximum = this.minimum * 100;
        }
    }

    /**
     * Increase num of workers.
     */
    public void addWorker() {
        this.numWorkers += 1;
    }

    /**
     * Decrease num of workers.
     */
    public void removeWorker() {
        this.numWorkers -= 1;
    }

    /**
     * Returns particular task.
     *
     * @return task for worker
     */
    public Task getTask() {
        if (numWorkers == maxUndistributed) {
            for (int i = 0; i < numWorkers; i++) {
                if (this.submissions.get(i) == null) {
                    return new Task(minimum, maximum, i, numWorkers, knownPrimes);
                }
            }
        }
        Task task = new Task(minimum, maximum, maxUndistributed, numWorkers, knownPrimes);
        maxUndistributed += 1;
        return task;
    }

    /**
     * Takes task solution and considers it as solved.
     *
     * @param submission submission by worker
     */
    public void submitTask(Submission submission) {
        if (this.maximum == submission.maximum
                && this.minimum == submission.minimum
                && this.numWorkers == submission.numWorkers) {
            this.submissions.set(submission.step, submission);
        } else {
            return;
        }

        for (var localSubmission : this.submissions) {
            if (localSubmission == null) {
                return;
            }
        }

        knownPrimes.append(new PrimesDelta(submissions));
        setUpTask();
    }
}
