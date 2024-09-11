package ru.nsu.shebanov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    public void testCardCreation() {
        // Arrange
        String expectedName = "король черви";
        int expectedWeight = 10;

        // Act
        Card card = new Card(expectedName, expectedWeight);

        // Assert
        assertEquals(expectedName, card.name);
        assertEquals(expectedWeight, card.weight);
    }

    @Test
    public void testToString() {
        // Arrange
        String name = "король трефы";
        int weight = 10;
        Card card = new Card(name, weight);

        // Act
        String result = card.toString();

        // Assert
        String expected = "король трефы (10)";
        assertEquals(expected, result);
    }
}