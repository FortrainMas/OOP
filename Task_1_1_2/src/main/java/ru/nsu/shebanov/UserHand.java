package ru.nsu.shebanov;

import java.util.ArrayList;

public class UserHand extends Hand {

    UserHand(Card card1, Card card2) {
        super(card1, card2);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Ваши карты: [");
        ArrayList<String> cardStrings = new ArrayList<>();

        int aceWeight = this.aceWeight();
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

        return result.toString();
    }
}
