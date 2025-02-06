package ru.nsu.shebanov.primes;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for non-prime computing in multithreading.
 */
public class MultiThreadCompute implements ComputerPrimes {
    private List<Integer> list;
    private int threadsNumber = 5;

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
     * Set specific number of threads.
     *
     * @param threadsNumber number of needed threads
     */
    public void setThreadsNumber(int threadsNumber) {
        this.threadsNumber = threadsNumber;
    }

    /**
     * Check if non-prime is in the list.
     *
     * @return result of operation
     */
    @Override
    public boolean compute() {
        PrimeThread.resetResult();

        int length = list.size();
        int stepSize = (int) Math.ceil((double) length / threadsNumber);

        List<Thread> threads = new ArrayList<>();

        int l = 0;
        int r = stepSize;
        while (l != length) {
            PrimeThread pt = new PrimeThread(this.list, l, r);
            threads.add(pt);
            pt.start();

            l = r;
            r = Math.min(r + stepSize, length);
        }

        for (var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return PrimeThread.result;
    }
}
