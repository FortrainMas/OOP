package ru.nsu.shebanov.blackjack;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Controls game deck.
 */
public class Deck {

    public ArrayList<Card> cards;

    Deck() {
        initDeck();
    }

    /**
     * Initialize new filled deck.
     */
    private void initDeck() {
        cards = new ArrayList<>();
        Rank[] cardRanks = Rank.values();
        Suit[] suits = Suit.values();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 4; j++) {
                this.cards.add(new Card(suits[j], cardRanks[i], i + 2));
            }
        }

        for (int i = 9; i < 12; i++) {
            for (int j = 0; j < 4; j++) {
                this.cards.add(new Card(suits[j], cardRanks[i], 10));
            }
        }

        for (int j = 0; j < 4; j++) {
            this.cards.add(new Card(suits[j], cardRanks[12], 11));
        }
        shuffle();
    }

    /**
     * Shuffles deck.
     */
    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    /**
     * Get upper card from the deck.
     *
     * @return this card
     */
    public Card getCard() {
        if (this.cards.isEmpty()) {
            initDeck();
        }
        return this.cards.remove(this.cards.size() - 1);
    }
}
