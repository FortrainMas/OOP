package ru.nsu.shebanov.primes.common;

import java.util.List;

public class Task {
    public int minimum;
    public int maximum;
    public int step;
    public int numWorkers;
    public KnownPrimes knownPrimes;

    public Task() {}

    public Task(int minimum, int maximum, int step, int numWorkers, KnownPrimes knownPrimes) {
        this.minimum = minimum;
        this.maximum = maximum;
        this.step = step;
        this.numWorkers = numWorkers;
        this.knownPrimes = knownPrimes;
    }

}
