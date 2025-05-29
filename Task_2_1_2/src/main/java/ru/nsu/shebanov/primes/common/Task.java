package ru.nsu.shebanov.primes.common;

import java.util.List;

/**
 * Task.
 */
public class Task {
    public int minimum;
    public int maximum;
    public int step;
    public int numWorkers;
    public KnownPrimes knownPrimes;

    /**
     * Jackson needs that.
     */
    public Task() {}

    /**
     * Intended constructor.
     *
     * @param minimum minimum to check
     * @param maximum maximum to check
     * @param step step for particular worker
     * @param numWorkers number of workers
     * @param knownPrimes list of known primes
     */
    public Task(int minimum, int maximum, int step, int numWorkers, KnownPrimes knownPrimes) {
        this.minimum = minimum;
        this.maximum = maximum;
        this.step = step;
        this.numWorkers = numWorkers;
        this.knownPrimes = knownPrimes;
    }
}
