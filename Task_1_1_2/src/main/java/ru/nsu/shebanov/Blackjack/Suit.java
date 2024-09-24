package ru.nsu.shebanov.Blackjack;

/**
 * Card suit enum.
 */
public enum Suit {
    HEARTS("черви"),
    DIAMONDS("буби"),
    CLUBS("трефы"),
    SPADES("пики");

    private final String suitName;

    /**
     * Constructor.
     *
     * @param suitName suitName as a string
     */
    Suit(String suitName) {
        this.suitName = suitName;
    }

    /**
     * Get card suit a string.
     *
     * @return card suit
     */
    @Override
    public String toString() {
        return suitName;
    }
}
