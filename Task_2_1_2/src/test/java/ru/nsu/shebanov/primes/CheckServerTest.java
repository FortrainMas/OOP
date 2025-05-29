package ru.nsu.shebanov.primes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ru.nsu.shebanov.primes.server.Server;

import org.junit.jupiter.api.Test;

/** Checks server behavior. */
public class CheckServerTest {
    @Test
    void dryRun() throws IOException {
        List<Integer> lst = new ArrayList<>();
        lst.add(1);
        Server server = new Server(1488, lst);
        server.run();
    }
}
