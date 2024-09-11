package ru.nsu.shebanov;

import java.util.ArrayList;

public class DealerHand extends Hand {
    boolean handRevealed = false;

    DealerHand(Card card1, Card card2) {
        super(card1, card2);
    }

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
            result.append(this.count_cards());
        } else {
            result.append(this.cards.get(0).toString());
            result.append(", <закрытая карта> ]");
        }

        return result.toString();
    }
}
