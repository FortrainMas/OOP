package ru.nsu.shebanov.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test the deck class.
 */
public class DeckTest {
    private final int initialCardNumber = 52;


    private Deck deck;

    /**
     * Setups basic deck.
     */
    @BeforeEach
    public void setUp() {
        deck = new Deck();
    }

    /**
     * Checks deck initialization.
     */
    @Test
    public void testDeckInitialization() {
        assertEquals(52, deck.cards.size());
    }

    /**
     * Checks if decks consists of unique cards.
     */
    @Test
    public void testDeckContainsUniqueCards() {
        HashSet<String> cardSet = new HashSet<>();

        for (Card card : deck.cards) {
            cardSet.add(card.name);
        }

        assertEquals(initialCardNumber, cardSet.size());
    }

    /**
     * Check does shuffling work.
     */
    @Test
    public void testShuffle() {
        ArrayList<Card> originalOrder = new ArrayList<>(deck.cards);

        deck.shuffle();

        assertNotEquals(originalOrder, deck.cards);
    }

    /**
     * Check getting card from the deck.
     */
    @Test
    public void testGetCard() {
        Card retrievedCard = deck.getCard();

        assertEquals(initialCardNumber-1, deck.cards.size());

        assertNotNull(retrievedCard);
        assertInstanceOf(Card.class, retrievedCard);
    }

    /**
     * Check behaviour of getting new deck.
     */
    @Test
    public void testGetCardEmptyDeck() {
        for (int i = 0; i < initialCardNumber; i++) {
            deck.getCard();
        }

        assertTrue(deck.cards.isEmpty());

        Card newCard = deck.getCard();
        assertNotNull(newCard);
        assertEquals(initialCardNumber-1, deck.cards.size());
    }
}
