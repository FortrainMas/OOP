package ru.nsu.shebanov.primes.common;

import java.util.Set;

/**
 * Submission.
 */
public class Submission {
    public int minimum;
    public int maximum;
    public int step;
    public int numWorkers;
    public Set<Integer> primesDelta;

    /**
     * For jackson.
     */
    public Submission() {}

    /**
     *  Intended constructor.
     *
     * @param minimum from task
     * @param maximum from task
     * @param step from task
     * @param numWorkers from task
     * @param primesDelta found primes
     */
    public Submission(
            int minimum, int maximum, int step, int numWorkers, Set<Integer> primesDelta) {
        this.minimum = minimum;
        this.maximum = maximum;
        this.step = step;
        this.numWorkers = numWorkers;
        this.primesDelta = primesDelta;
    }
}
