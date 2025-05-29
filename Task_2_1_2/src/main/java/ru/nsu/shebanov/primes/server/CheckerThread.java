package ru.nsu.shebanov.primes.server;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.shebanov.primes.common.KnownPrimes;

/** Separate thread task to check the numbers. */
public class CheckerThread implements Runnable {
    public final KnownPrimes knownPrimes;
    public final List<Integer> checkTask;
    public final CheckResult checkResult;

    /**
     * Configure the task.
     *
     * @param checkTask sequence of numbers to check.
     * @param knownPrimes mutable known primes list
     * @param checkResult immutable results of the check
     */
    public CheckerThread(
            List<Integer> checkTask, KnownPrimes knownPrimes, CheckResult checkResult) {
        this.checkTask = checkTask;
        this.knownPrimes = knownPrimes;
        this.checkResult = checkResult;
    }

    /** Run the thread. */
    @Override
    public void run() {
        List<Integer> untestedIntegers = new ArrayList<>();
        for (int i = 0; i < this.checkTask.size(); i++) {
            untestedIntegers.add(i);
        }
        int left = 0;
        int right = 0;
        int rightEdge = checkTask.size();

        while (rightEdge != 0) {

            int rightIndex = untestedIntegers.get(right);
            int rightValue = checkTask.get(rightIndex);
            if (rightValue > this.knownPrimes.maximum) {
                untestedIntegers.set(left, rightIndex);
                left += 1;
                right += 1;
            } else if (this.knownPrimes.primes.contains(rightValue)) {
                right += 1;
            } else {
                checkResult.foundPrime = true;
                return;
            }

            if (right == rightEdge + 1) {
                rightEdge = left;
                right = 0;
                left = 0;
            }
        }
        checkResult.foundPrime = false;
    }
}
