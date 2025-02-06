package ru.nsu.shebanov.primes;

import java.util.List;

public class PrimeThread extends Thread {
    private final List<Integer> list;
    private final int start;
    private final int end;
    public static volatile boolean result = false;
    public static void resetResult() {
        result = false;
    }

    public PrimeThread(List<Integer> list, int start, int end) {
        this.list = list;
        this.start = start;
        this.end = end;
    }

    private boolean isPrime(int x) {
        for(int i = 2; i * i <= x; i++){
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void run() {
        for(int i = this.start; i < this.end && !result; i++){
            if (!isPrime(list.get(i))) {
                result = true;
                break;
            }
        }
    }
}
