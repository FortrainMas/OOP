package ru.nsu.shebanov.Blackjack;

import java.util.ArrayList;

/**
 * Dealer hand realisation.
 */
public class DealerHand extends Hand {
    boolean handRevealed = false;

    DealerHand(Card card1, Card card2) {
        super(card1, card2);
    }

    /**
     * Makes specified print type of dealer hand.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Карты диллера: [ ");
        ArrayList<String> cardStrings = new ArrayList<>();

        int aceWeight = this.aceWeight();

        if (this.handRevealed) {
            for (Card card : this.cards) {
                if (card.weight == 11) {
                    cardStrings.add(card.name + " (" + aceWeight + ")");
                } else {
                    cardStrings.add(card.name + " (" + card.weight + ")");
                }
            }
            result.append(String.join(", ", cardStrings));
            result.append("] => ");
            result.append(this.countCards());
        } else {
            result.append(this.cards.get(0).toString());
            result.append(", <закрытая карта> ]");
        }

        return result.toString();
    }
}
