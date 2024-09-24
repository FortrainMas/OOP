package ru.nsu.shebanov.blackjack;


/**
 * Enum for card ranks.
 */
public enum Rank {
    ACE("туз"),
    TWO("двойка"),
    THREE("тройка"),
    FOUR("четвёрка"),
    FIVE("пятёрка"),
    SIX("шестерка"),
    SEVEN("семерка"),
    EIGHT("восьмёрка"),
    NINE("девятка"),
    TEN("десятка"),
    JACK("валет"),
    QUEEN("дама"),
    KING("король");

    final String cardName;

    /**
     * Constructor for card rank.
     *
     * @param cardName name for card rank
     */
    Rank(String cardName) {
        this.cardName = cardName;
    }

    /**
     * Get card rank as a string.
     *
     * @return rank
     */
    @Override
    public String toString() {
        return cardName;
    }
}
