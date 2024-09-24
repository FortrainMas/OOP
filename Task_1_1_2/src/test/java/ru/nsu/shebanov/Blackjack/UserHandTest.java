package ru.nsu.shebanov.Blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for user hand.
 */
public class UserHandTest {

    private Card cardAce;
    private Card cardTen;
    private Card cardNine;
    private Card cardFive;
    private Card cardAnotherAce;
    private DealerHand dealerHand;

    private UserHand userHand;

    /**
     * Set up basic conditions for tests.
     */
    @BeforeEach
    public void setUp() {
        cardAce = new Card(Suit.SPADES, Rank.ACE, 11);
        cardTen = new Card(Suit.SPADES, Rank.TEN, 10);
        cardNine = new Card(Suit.SPADES, Rank.NINE, 9);
        cardFive = new Card(Suit.SPADES, Rank.FIVE, 5);
        cardAnotherAce = new Card(Suit.HEARTS, Rank.ACE, 11);
        dealerHand = new DealerHand(cardAce, cardNine);

        userHand = new UserHand(cardAce, cardTen);
    }

    /**
     * Tests for string which gets proceeded if we add ace and ten to the basic hand.
     */
    @Test
    public void testToStringWithAceAndTen() {
        String expected = "Ваши карты: [туз пики (11), десятка пики (10)] => 21";
        assertEquals(expected, userHand.toString());
    }

    /**
     * Tests for string which gets proceeded if we add another ace.
     */
    @Test
    public void testToStringWithMultipleAces() {
        userHand.add_card(cardAnotherAce);
        String expected =
                "Ваши карты: [туз пики (1), десятка пики (10), туз черви (1)] => 12";
        assertEquals(expected, userHand.toString());
    }

    /**
     * Test for another hand without aces.
     */
    @Test
    public void testToStringWithoutAces() {
        UserHand simpleHand = new UserHand(cardNine, cardFive);
        String expected = "Ваши карты: [девятка пики (9), пятёрка пики (5)] => 14";
        assertEquals(expected, simpleHand.toString());
    }

    /**
     * Tests for string which gets proceeded if we make hand of two aces.
     */
    @Test
    public void testToStringWithTwoAces() {
        UserHand doubleAceHand = new UserHand(cardAce, cardAnotherAce);
        String expected = "Ваши карты: [туз пики (1), туз черви (1)] => 2";
        assertEquals(expected, doubleAceHand.toString());
    }


    /**
     * Tests for string which gets proceeded if we make hand of diverse cards.
     */
    @Test
    public void testToStringWithMixedCards() {
        userHand.add_card(cardNine);
        String expected =
                "Ваши карты: [туз пики (1), десятка пики (10), девятка пики (9)] => 20";
        assertEquals(expected, userHand.toString());
    }

    /**
     * Tests for string which gets proceeded if we make a string where aces should be counted as 1.
     */
    @Test
    public void testToStringWithCountedAceAsOne() {
        userHand.add_card(cardAce); // Add another Ace
        String expected = "Ваши карты: [туз пики (1), десятка пики (10), туз пики (1)] => 12";
        assertEquals(expected, userHand.toString());
    }
}
