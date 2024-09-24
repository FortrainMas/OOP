package ru.nsu.shebanov.blackjack;

/**
 * Card class.
 */
public class Card {
    public final Suit suit;
    public final Rank rank;
    public final String name;
    int weight;

    Card(Suit suit, Rank rank, int weight) {
        this.suit = suit;
        this.rank = rank;
        this.name = this.rank.toString() + " " + this.suit.toString();
        this.weight = weight;
    }

    /**
     * Prints card.
     *
     * @return card_name (card_weight)
     */
    @Override
    public String toString() {
        return this.name + " (" + this.weight + ")";
    }
}
