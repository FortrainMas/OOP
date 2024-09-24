package ru.nsu.shebanov.blackjack;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Controls game deck.
 */
public class Deck {

    ArrayList<Card> cards;

    Deck() {
        initDeck();
    }

    /**
     * Initialize new filled deck.
     */
    private void initDeck() {
        cards = new ArrayList<>();
        Rank[] cardRanks =
                new Rank[] {
                    Rank.NINE,
                    Rank.EIGHT,
                    Rank.SEVEN,
                    Rank.SIX,
                    Rank.FIVE,
                    Rank.FOUR,
                    Rank.THREE,
                    Rank.TWO,
                    Rank.TEN,
                    Rank.QUEEN,
                    Rank.KING,
                    Rank.JACK,
                    Rank.ACE
                };

        Suit[] suits = new Suit[] {Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS, Suit.SPADES};

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
