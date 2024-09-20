package ru.nsu.shebanov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstantsTest {
    @Test
    void testConstants(){
        assertEquals(Constants.ACE_WEIGHT_DEFAULT, 11);
        assertEquals(Constants.ACE_WEIGHT_LOWERED, 1);
        assertEquals(Constants.BLACKJACK_SCORE, 21);
        assertEquals(Constants.DEALER_STOP_SCORE, 17);
        assertEquals(Constants.ROUNDS_NUMBER, 100);
    }
}
