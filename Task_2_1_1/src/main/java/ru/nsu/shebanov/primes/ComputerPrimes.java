package ru.nsu.shebanov.primes;

import java.util.List;

/**
 * Interface for all computers.
 */
public interface ComputerPrimes {
    /**
     * Set list to find non-primes.
     *
     * @param list list to search in
     */
    public void setList(List<Integer> list);

    /**
     * Check if non-prime is in the list.
     *
     * @return result of operation
     */
    public boolean compute();
}
