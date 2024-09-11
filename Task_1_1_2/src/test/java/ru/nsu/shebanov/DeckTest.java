package ru.nsu.shebanov;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;

public class DeckTest {

    private Deck deck;

    @BeforeEach
    public void setUp() {
        deck = new Deck();
    }

    @Test
    public void testDeckInitialization() {
        // Test that the deck initializes with the correct number of cards
        assertEquals(52, deck.cards.size()); // 36 cards in the deck
    }

    @Test
    public void testDeckContainsUniqueCards() {
        // Test that all cards in the deck are unique
        HashSet<String> cardSet = new HashSet<>();

        for (Card card : deck.cards) {
            cardSet.add(card.name);
        }

        assertEquals(52, cardSet.size()); // 36 unique cards
    }

    @Test
    public void testShuffle() {
        // Get the order of cards before shuffling
        ArrayList<Card> originalOrder = new ArrayList<>(deck.cards);

        // Shuffle the deck
        deck.shuffle();

        // Check that the order has changed (not guaranteed, but should be likely)
        assertNotEquals(originalOrder, deck.cards);
    }

    @Test
    public void testGetCard() {
        // Get a card from the deck
        Card retrievedCard = deck.getCard();

        // Check that the deck size is reduced by one
        assertEquals(51, deck.cards.size());

        // Ensure that the card is not null and is an instance of Card
        assertNotNull(retrievedCard);
        assertInstanceOf(Card.class, retrievedCard);
    }

    @Test
    public void testGetCardEmptyDeck() {
        // Remove all cards from the deck
        for (int i = 0; i < 52; i++) {
            deck.getCard();
        }

        // The deck should be empty
        assertTrue(deck.cards.isEmpty());

        // Get card should reinitialize the deck and then return a card
        Card newCard = deck.getCard();
        assertNotNull(newCard); // Ensure new card is returned
        assertEquals(51, deck.cards.size()); // Deck should have 35 cards after getting one
    }
}
