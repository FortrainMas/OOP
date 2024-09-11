package ru.nsu.shebanov;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class UserHandTest {

    private Card cardAce;
    private Card cardTen;
    private Card cardNine;
    private Card cardFive;
    private Card cardAnotherAce;

    private UserHand userHand;

    @BeforeEach
    public void setUp() {
        cardAce = new Card("туз пики", 11);
        cardTen = new Card("десятка пики", 10);
        cardNine = new Card("девятка пики", 9);
        cardFive = new Card("пятёрка пики", 5);
        cardAnotherAce = new Card("туз черви", 11);

        userHand = new UserHand(cardAce, cardTen); // initial hand with Ace and Ten
    }

    @Test
    public void testToStringWithAceAndTen() {
        String expected = "Ваши карты: [туз пики (11), десятка пики (10)] => 21";
        assertEquals(expected, userHand.toString());
    }

    @Test
    public void testToStringWithMultipleAces() {
        userHand.add_card(cardAnotherAce); // Adding another Ace
        String expected =
                "Ваши карты: [туз пики (1), десятка пики (10), туз черви (1)] => 12"; // should show
                                                                                      // 11 for the
                                                                                      // first Ace
                                                                                      // and 1 for
                                                                                      // the second
                                                                                      // Ace
        assertEquals(expected, userHand.toString());
    }

    @Test
    public void testToStringWithoutAces() {
        UserHand simpleHand = new UserHand(cardNine, cardFive);
        String expected = "Ваши карты: [девятка пики (9), пятёрка пики (5)] => 14";
        assertEquals(expected, simpleHand.toString());
    }

    @Test
    public void testToStringWithTwoAces() {
        UserHand doubleAceHand = new UserHand(cardAce, cardAnotherAce);
        String expected = "Ваши карты: [туз пики (1), туз черви (1)] => 2";
        assertEquals(expected, doubleAceHand.toString());
    }

    // Test with more diverse cards
    @Test
    public void testToStringWithMixedCards() {
        userHand.add_card(cardNine); // add a nine to the hand
        String expected =
                "Ваши карты: [туз пики (1), десятка пики (10), девятка пики (9)] => 20"; // 11 + 10
                                                                                         // + 9 = 30
        assertEquals(expected, userHand.toString());
    }

    // Test with Ace that would be counted as 1
    @Test
    public void testToStringWithCountedAceAsOne() {
        userHand.add_card(cardAce); // Add another Ace
        String expected =
                "Ваши карты: [туз пики (1), десятка пики (10), туз пики (1)] => 12";
        assertEquals(expected, userHand.toString());
    }
}
