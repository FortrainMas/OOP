package ru.nsu.shebanov.primes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import ru.nsu.shebanov.primes.common.KnownPrimes;
import ru.nsu.shebanov.primes.common.PrimesDelta;
import ru.nsu.shebanov.primes.common.Submission;
import ru.nsu.shebanov.primes.common.Task;

/** Test everything for good. */
public class CommonsTest {

    @Test
    void fullStory() {

        KnownPrimes knownPrimes = new KnownPrimes();
        Task task = new Task(3, 9, 1, 1, knownPrimes);

        Set<Integer> primes = new HashSet<>();
        primes.add(3);
        primes.add(5);
        primes.add(7);
        Submission submission = new Submission(3, 9, 1, 1, primes);
        List<Submission> submissions = new ArrayList<>();
        submissions.add(submission);
        PrimesDelta primesDelta = new PrimesDelta(submissions);
        knownPrimes.append(primesDelta);
    }
}
