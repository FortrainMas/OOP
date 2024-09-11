package ru.nsu.shebanov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for abstract class Hand.
 */
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

    /**
     * Setups variables and make certain class for hands.
     */
    @BeforeEach
    public void setUp() {
        cardAce = new Card("туз черви", 11);
        cardTen = new Card("десятка пики", 10);
        cardNine = new Card("девятка буби", 9);
        cardFive = new Card("пятёрка пики", 5);

        hand = new ConcreteHand(cardAce, cardTen);
    }

    /**
     * Check countCards.
     */
    @Test
    public void testCountCardsWithAceAndTen() {
        assertEquals(21, hand.countCards());
    }

    /**
     * Check countCards.
     */
    @Test
    public void testCountCardsWithMultipleAces() {
        hand.add_card(cardAce);
        assertEquals(12, hand.countCards());

        // Adding another ace
        hand.add_card(cardAce);
        assertEquals(13, hand.countCards());
    }

    /**
     * Check countCards.
     */
    @Test
    public void testCountCardsWithoutAces() {
        Hand simpleHand = new ConcreteHand(cardNine, cardFive);
        assertEquals(14, simpleHand.countCards());
    }

    /**
     * Check countCards.
     */
    @Test
    public void testAce11Counter() {
        assertEquals(11, hand.aceWeight());

        hand.add_card(cardFive);
        assertEquals(1, hand.aceWeight());

        hand.add_card(new Card("Another Ace", 11));
        assertEquals(1, hand.aceWeight());
    }

    /**
     * Check countCards.
     */
    @Test
    public void testMultipleAcesCount() {
        hand.add_card(cardAce);
        assertEquals(1, hand.aceWeight());
    }


}
