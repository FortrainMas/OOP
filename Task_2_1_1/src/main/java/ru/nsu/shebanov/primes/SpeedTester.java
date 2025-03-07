package ru.nsu.shebanov.primes;

import java.util.ArrayList;
import java.util.List;

/**
 * Tester for stats.
 */
public class SpeedTester {
    private static final int LIMIT = 100_000_0;

    /**
     * Generate list of all prime less than LIMIT.
     *
     * @return list of primes
     */
    private static List<Integer> generatePrimes() {
        boolean[] sieve = new boolean[SpeedTester.LIMIT + 1];
        List<Integer> primes = new ArrayList<>();

        for (int i = 2; i * i <= SpeedTester.LIMIT; i++) {
            if (!sieve[i]) {
                for (int j = i * i; j <= SpeedTester.LIMIT; j += i) {
                    sieve[j] = true;
                }
            }
        }

        for (int i = 2; i <= SpeedTester.LIMIT; i++) {
            if (!sieve[i]) {
                primes.add(i);
            }
        }
        return primes;
    }

    /**
     * Test for a computer.
     *
     * @param primes list of prime numbers
     * @param cp computer
     * @return time in nanoseconds
     */
    private static long testComputer(List<Integer> primes, ComputerPrimes cp) {
        cp.setList(primes);
        long res = 0;
        int testsNumber = 10;
        for (int i = 0; i < testsNumber; i++) {
            long startTime = System.nanoTime();
            cp.compute();
            long endTime = System.nanoTime();
            res += (endTime - startTime);
        }
        return res / testsNumber;
    }

    /**
     * Run tests on all kind of computers.
     *
     * @param args process args
     */
    public static void main(String[] args) {
        List<Integer> primes = generatePrimes();

        SingleThreadCompute stc = new SingleThreadCompute();
        long stcResult = testComputer(primes, stc);
        System.out.println("Single thread avg: "
                + String.format("%e", (double) stcResult) + "ns");

        MultiThreadCompute mtc = new MultiThreadCompute();
        mtc.setThreadsNumber(1);
        long mtcResult = testComputer(primes, mtc);
        System.out.println("Multithreading(1) avg: "
                + String.format("%e", (double) mtcResult) + "ns");

        mtc.setThreadsNumber(2);
        mtcResult = testComputer(primes, mtc);
        System.out.println("Multithreading(2) avg: "
                + String.format("%e", (double) mtcResult) + "ns");

        mtc.setThreadsNumber(3);
        mtcResult = testComputer(primes, mtc);
        System.out.println("Multithreading(3) avg: "
                + String.format("%e", (double) mtcResult) + "ns");

        mtc.setThreadsNumber(4);
        mtcResult = testComputer(primes, mtc);
        System.out.println("Multithreading(4) avg: "
                + String.format("%e", (double) mtcResult) + "ns");

        mtc.setThreadsNumber(5);
        mtcResult = testComputer(primes, mtc);
        System.out.println("Multithreading(5) avg: "
                + String.format("%e", (double) mtcResult) + "ns");

        mtc.setThreadsNumber(6);
        mtcResult = testComputer(primes, mtc);
        System.out.println("Multithreading(6) avg: "
                + String.format("%e", (double) mtcResult) + "ns");

        mtc.setThreadsNumber(200);
        mtcResult = testComputer(primes, mtc);
        System.out.println("Multithreading(200) avg: "
                + String.format("%e", (double) mtcResult) + "ns");

        ParallelStreamsCompute psc = new ParallelStreamsCompute();
        long pscResult = testComputer(primes, psc);
        System.out.println("Parallel streams avg: "
                + String.format("%e", (double) pscResult) + "ns");
    }
}
