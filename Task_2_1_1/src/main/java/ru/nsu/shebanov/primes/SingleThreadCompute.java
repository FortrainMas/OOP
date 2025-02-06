package ru.nsu.shebanov.primes;

import java.util.List;

public class SingleThreadCompute implements ComputerPrimes {
    private List<Integer> list;

    @Override
    public void setList(List<Integer> list) {
        this.list = list;
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
    public boolean compute() {
        for(var num : this.list) {
            if (!isPrime((num))) {
                return true;
            }
        }

        return false;
    }
}
