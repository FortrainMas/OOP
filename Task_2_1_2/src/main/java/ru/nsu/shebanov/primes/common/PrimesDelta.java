package ru.nsu.shebanov.primes.common;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrimesDelta {
    public int maximum;
    public Set<Integer> primes;

    public PrimesDelta() {}

    public PrimesDelta(int maximum, Set<Integer> primes) {
        this.maximum = maximum;
        this.primes = primes;
    }

    public PrimesDelta(List<Submission> submissionList) {
        this.maximum = submissionList.getFirst().maximum;
        this.primes = new HashSet<>();
        for(var submission : submissionList) {
            primes.addAll(submission.primesDelta);
        }
    }
}
