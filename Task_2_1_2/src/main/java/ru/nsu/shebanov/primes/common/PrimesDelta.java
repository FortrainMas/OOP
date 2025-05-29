package ru.nsu.shebanov.primes.common;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Some primes until maximum.
 */
public class PrimesDelta {
    public int maximum;
    public Set<Integer> primes;

    /**
     * For jackson.
     */
    public PrimesDelta() {}


    /**
     * Intended constructor, merge submissions.
     *
     * @param submissionList list of submission to merge
     */
    public PrimesDelta(List<Submission> submissionList) {
        this.maximum = submissionList.getFirst().maximum;
        this.primes = new HashSet<>();
        for (var submission : submissionList) {
            primes.addAll(submission.primesDelta);
        }
    }
}
