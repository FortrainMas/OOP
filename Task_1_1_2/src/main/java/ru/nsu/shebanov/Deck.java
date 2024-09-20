package ru.nsu.shebanov;

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
        String[] suits = {"черви", "трефы", "буби", "пики"};
        String[] cardNames =
                new String[] {
                    "двойка",
                    "тройка",
                    "четвёрка",
                    "пятёрка",
                    "шестёрка",
                    "семёрка",
                    "восьмёрка",
                    "девятка",
                    "десятка",
                    "валет",
                    "дама",
                    "король",
                    "туз"
                };
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 4; j++) {
                this.cards.add(new Card(cardNames[i] + " " + suits[j], i + 2));
            }
        }

        for (int i = 9; i < 12; i++) {
            for (int j = 0; j < 4; j++) {
                this.cards.add(new Card(cardNames[i] + " " + suits[j], 10));
            }
        }

        for (int j = 0; j < 4; j++) {
            this.cards.add(new Card(cardNames[12] + " " + suits[j], 11));
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
