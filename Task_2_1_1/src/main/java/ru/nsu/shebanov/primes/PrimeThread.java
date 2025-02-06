package ru.nsu.shebanov.primes;

import java.util.List;

/**
 * Class for computing thread.
 */
public class PrimeThread extends Thread {
    private final List<Integer> list;
    private final int start;
    private final int end;
    public static volatile boolean result = false;

    /**
     * Reset thread volatile data.
     */
    public static void resetResult() {
        result = false;
    }

    /**
     * Constructor for thread.
     *
     * @param list list to work on
     * @param start start of thread range
     * @param end end of thread range
     */
    public PrimeThread(List<Integer> list, int start, int end) {
        this.list = list;
        this.start = start;
        this.end = end;
    }


    /**
     * Function to determine if number is prime except for 1.
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
     * Function to run in thread.
     */
    @Override
    public void run() {
        for (int i = this.start; i < this.end && !result; i++) {
            if (!isPrime(list.get(i))) {
                result = true;
                break;
            }
        }
    }
}
