package ru.nsu.shebanov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DealerHandTest {

    private Card aceCard;
    private Card numberCard;
    private Card faceCard;
    private DealerHand dealerHand;

    @BeforeEach
    public void setUp() {
        aceCard = new Card("туз черви", 11); // Assuming you have a Card constructor
        numberCard = new Card("пятёрка черви", 5);
        faceCard = new Card("король пики", 10);
        dealerHand = new DealerHand(aceCard, numberCard);
    }

    @Test
    public void testInitialCountCards() {
        assertEquals(16, dealerHand.count_cards()); // 11 (Ace) + 5 = 16
    }

    @Test
    public void testAddCard() {
        dealerHand.add_card(faceCard);
        assertEquals(16, dealerHand.count_cards());
    }

    @Test
    public void testToStringRevealedHand() {
        dealerHand.handRevealed = true;
        String expected = "Карты диллера: [ туз черви (11), пятёрка черви (5)] => 16";
        assertEquals(expected, dealerHand.toString());
    }

    @Test
    public void testToStringHiddenHand() {
        dealerHand.handRevealed = false;
        String expected = "Карты диллера: [ туз черви (11), <закрытая карта> ]";
        assertEquals(expected, dealerHand.toString());
    }

    @Test
    public void testCountCardsWithMultipleAces() {
        dealerHand.add_card(aceCard); // Adding another Ace
        assertEquals(7, dealerHand.count_cards()); // 11 + 5 + 1 (second Ace as 1) = 17
    }
}
