package ru.nsu.shebanov.primes.common;

import java.util.Set;

public class Submission {
    public int minimum;
    public int maximum;
    public int step;
    public int numWorkers;
    public Set<Integer> primesDelta;

    public Submission() {}

    public Submission(int minimum, int maximum, int step, int numWorkers, Set<Integer> primesDelta) {
        this.minimum = minimum;
        this.maximum = maximum;
        this.step = step;
        this.numWorkers = numWorkers;
        this.primesDelta = primesDelta;
    }
}
