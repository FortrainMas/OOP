package ru.nsu.shebanov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for card class.
 */
public class CardTest {

    /**
     * Check for card print.
     */
    @Test
    public void testCardCreation() {
        String expectedName = "король черви";
        int expectedWeight = 10;

        Card card = new Card(expectedName, expectedWeight);

        assertEquals(expectedName, card.name);
        assertEquals(expectedWeight, card.weight);
    }

    /**
     * Check for card print.
     */
    @Test
    public void testToString() {
        String name = "король трефы";
        int weight = 10;
        Card card = new Card(name, weight);

        String result = card.toString();

        String expected = "король трефы (10)";
        assertEquals(expected, result);
    }
}
