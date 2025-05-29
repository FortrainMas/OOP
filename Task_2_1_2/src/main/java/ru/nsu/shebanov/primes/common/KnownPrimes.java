package ru.nsu.shebanov.primes.common;

import java.util.HashSet;
import java.util.Set;

/**
 * List of known primes less than maximum.
 */
public class KnownPrimes {
    public int maximum;
    public final Set<Integer> primes;

    /**
     * Constructor.
     */
    public KnownPrimes() {
        this.maximum = 2;
        this.primes = new HashSet<>();
        this.primes.add(2);
    }

    /**
     * Append new primes.
     *
     * @param primesDelta primes delta.
     */
    public synchronized void append(PrimesDelta primesDelta) {
        this.maximum = primesDelta.maximum;
        primes.addAll(primesDelta.primes);
    }
}
