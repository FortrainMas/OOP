package ru.nsu.shebanov.primes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.shebanov.primes.server.Server;

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
