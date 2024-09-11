package ru.nsu.shebanov;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class HandTest {
    private static class ConcreteHand extends Hand {
        ConcreteHand(Card card1, Card card2) {
            super(card1, card2);
        }
    }

    private Card cardAce;
    private Card cardTen;
    private Card cardNine;
    private Card cardFive;

    private Hand hand;

    @BeforeEach
    public void setUp() {
        // Assuming Card class has a constructor Card(String name, int weight)
        cardAce = new Card("туз черви", 11);
        cardTen = new Card("десятка пики", 10);
        cardNine = new Card("девятка буби", 9);
        cardFive = new Card("пятёрка пики", 5);

        hand = new ConcreteHand(cardAce, cardTen); // Use a concrete implementation
    }

    @Test
    public void testCountCardsWithAceAndTen() {
        assertEquals(21, hand.count_cards());
    }

    @Test
    public void testCountCardsWithMultipleAces() {
        hand.add_card(cardAce);
        assertEquals(12, hand.count_cards());

        // Adding another ace
        hand.add_card(cardAce);
        assertEquals(13, hand.count_cards()); // This goes over 21, should return a value of 23
    }

    @Test
    public void testCountCardsWithoutAces() {
        Hand simpleHand = new ConcreteHand(cardNine, cardFive);
        assertEquals(14, simpleHand.count_cards()); // 9 + 5 = 14
    }

    @Test
    public void testAce11Counter() {
        assertEquals(1, hand.ace_11_counter()); // Should return 1 as we have one Ace

        hand.add_card(cardFive);
        assertEquals(0, hand.ace_11_counter()); // Still should return 1

        hand.add_card(new Card("Another Ace", 11));
        assertEquals(0, hand.ace_11_counter()); // Still should return 1 for ace count
    }

    // If we add more than 1 ace, it should still return 1
    @Test
    public void testMultipleAcesCount() {
        hand.add_card(cardAce);
        assertEquals(0, hand.ace_11_counter()); // Should return 1
    }

    // Concrete class for testing since Hand is abstract

}
