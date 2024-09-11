package ru.nsu.shebanov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests dealer hand.
 */
public class DealerHandTest {

    private Card aceCard;
    private Card numberCard;
    private Card faceCard;
    private DealerHand dealerHand;

    /**
     * Setups basic dealer hand.
     */
    @BeforeEach
    public void setUp() {
        aceCard = new Card("туз черви", 11);
        numberCard = new Card("пятёрка черви", 5);
        faceCard = new Card("король пики", 10);
        dealerHand = new DealerHand(aceCard, numberCard);
    }

    /**
     * Tests initial score.
     */
    @Test
    public void testInitialCountCards() {
        assertEquals(16, dealerHand.countCards());
    }

    /**
     * Tests score after adding car.
     */
    @Test
    public void testAddCard() {
        dealerHand.add_card(faceCard);
        assertEquals(16, dealerHand.countCards());
    }

    /**
     * Tests for hand print.
     */
    @Test
    public void testToStringRevealedHand() {
        dealerHand.handRevealed = true;
        String expected = "Карты диллера: [ туз черви (11), пятёрка черви (5)] => 16";
        assertEquals(expected, dealerHand.toString());
    }

    /**
     * Tests for hand print before revealing the card.
     */
    @Test
    public void testToStringHiddenHand() {
        dealerHand.handRevealed = false;
        String expected = "Карты диллера: [ туз черви (11), <закрытая карта> ]";
        assertEquals(expected, dealerHand.toString());
    }

    /**
     * Tests for hand print with multiple aces.
     */
    @Test
    public void testCountCardsWithMultipleAces() {
        dealerHand.add_card(aceCard);
        assertEquals(7, dealerHand.countCards());
    }
}
