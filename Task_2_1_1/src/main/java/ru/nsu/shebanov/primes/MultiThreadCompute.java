package ru.nsu.shebanov.primes;

import java.util.ArrayList;
import java.util.List;

public class MultiThreadCompute implements ComputerPrimes{
    private List<Integer> list;
    private int threadsNumber = 5;

    @Override
    public void setList(List<Integer> list) {
        this.list = list;
    }

    public void setThreadsNumber(int threadsNumber) {
        this.threadsNumber = threadsNumber;
    }

    @Override
    public boolean compute() {
        PrimeThread.resetResult();

        int length = list.size();
        int stepSize = (int) Math.ceil((double) length / threadsNumber);

        List<Thread> threads = new ArrayList<>();

        int l = 0;
        int r = stepSize;
        while(l != length){
            PrimeThread pt = new PrimeThread(this.list, l, r);
            threads.add(pt);
            pt.start();

            l = r;
            r = Math.min(r + stepSize, length);
        }

        for(var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return PrimeThread.result;
    }
}
