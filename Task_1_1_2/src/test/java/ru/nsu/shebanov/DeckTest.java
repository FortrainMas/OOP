package ru.nsu.shebanov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class DeckTest {

    private Deck deck;

    @BeforeEach
    public void setUp() {
        deck = new Deck();
    }

    @Test
    public void testDeckInitialization() {
        assertEquals(52, deck.cards.size());
    }

    @Test
    public void testDeckContainsUniqueCards() {
        HashSet<String> cardSet = new HashSet<>();

        for (Card card : deck.cards) {
            cardSet.add(card.name);
        }

        assertEquals(52, cardSet.size());
    }

    @Test
    public void testShuffle() {
        ArrayList<Card> originalOrder = new ArrayList<>(deck.cards);

        deck.shuffle();

        assertNotEquals(originalOrder, deck.cards);
    }

    @Test
    public void testGetCard() {
        Card retrievedCard = deck.getCard();

        assertEquals(51, deck.cards.size());

        assertNotNull(retrievedCard);
        assertInstanceOf(Card.class, retrievedCard);
    }

    @Test
    public void testGetCardEmptyDeck() {
        for (int i = 0; i < 52; i++) {
            deck.getCard();
        }

        assertTrue(deck.cards.isEmpty());

        Card newCard = deck.getCard();
        assertNotNull(newCard);
        assertEquals(51, deck.cards.size());
    }
}
