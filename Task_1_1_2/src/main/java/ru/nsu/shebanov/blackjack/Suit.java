package ru.nsu.shebanov.blackjack;

/**
 * Card suit enum.
 */
public enum Suit {
    HEARTS("черви"),
    DIAMONDS("буби"),
    CLUBS("трефы"),
    SPADES("пики");

    private String suitName;

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
