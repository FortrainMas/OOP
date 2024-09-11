package ru.nsu.shebanov;

/**
 * Card class.
 */
public class Card {
    String name;
    int weight;

    Card(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    /**
     * Prints card.
     * @return card_name (card_weight)
     */
    @Override
    public String toString() {
        return this.name + " (" + this.weight + ")";
    }
}
