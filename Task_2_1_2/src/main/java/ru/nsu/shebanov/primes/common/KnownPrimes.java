package ru.nsu.shebanov.primes.common;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KnownPrimes {
    public int maximum;
    public final Set<Integer> primes;

    public KnownPrimes() {
        this.maximum = 2;
        this.primes = new HashSet<>();
    }



    public synchronized void append(PrimesDelta primesDelta) {
        this.maximum = primesDelta.maximum;
        primes.addAll(primesDelta.primes);
    }
}
