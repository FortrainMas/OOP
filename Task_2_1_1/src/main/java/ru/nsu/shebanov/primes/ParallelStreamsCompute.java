package ru.nsu.shebanov.primes;

import java.util.List;

/**
 * Class for non-prime computing with parallel streams.
 */
public class ParallelStreamsCompute implements ComputerPrimes {
    private List<Integer> list;

    /**
     * Set list to find non-primes.
     *
     * @param list list to search in
     */
    @Override
    public void setList(List<Integer> list) {
        this.list = list;
    }

    /**
     * Function to determine if number is prime except for 1.
     *
     * @param x number to check
     * @return is number prime or prime for 1
     */
    private boolean isPrime(int x) {
        for (int i = 2; i * i <= x; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if non-prime is in the list.
     *
     * @return result of operation
     */
    @Override
    public boolean compute() {
        return list
                .parallelStream()
                .map(this::isPrime)
                .anyMatch(x -> !x);
    }
}
