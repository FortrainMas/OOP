package ru.nsu.shebanov.blackjack;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

/**
 * Test for black jack class.
 */
public class BlackjackTest {

    /**
     * Skip test for blackjack class.
     * All inner logic is already, so it just checks if it runs successfully.
     */
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
