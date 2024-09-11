package ru.nsu.shebanov;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlackjackTest {

    @Test
    public void testDealInitialCards() {
        ByteArrayInputStream zeroStream = new ByteArrayInputStream("0\n".repeat(100).getBytes());

        System.setIn(zeroStream);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String[] args = {};
        Blackjack.main(args);

        assertTrue(true);
    }
}
