package ru.nsu.shebanov;

import java.util.ArrayList;

/**
 * Controls hand behaviour.
 */
public abstract class Hand {
    public ArrayList<Card> cards;

    Hand(Card card1, Card card2) {
        this.cards = new ArrayList<Card>();
        this.cards.add(card1);
        this.cards.add(card2);
    }

    /**
     * Adds new card in the hand.
     *
     * @param card added card
     */
    public void addCard(Card card) {
        this.cards.add(card);
    }

    /**
     * Counts score of cards in hand.
     *
     * @return score
     */
    public int countCards() {
        int sum = 0;
        int aceCounter = 0;
        for (Card card : this.cards) {
            if (card.weight != Constants.ACE_WEIGHT_DEFAULT) {
                sum += card.weight;
            } else {
                aceCounter += 1;
            }
        }

        if (aceCounter > 0
                && aceCounter * Constants.ACE_WEIGHT_DEFAULT + sum <= Constants.BLACKJACK_SCORE) {
            sum += Constants.ACE_WEIGHT_DEFAULT;
        } else if (aceCounter > 0) {
            sum += aceCounter;
        }
        return sum;
    }

    /**
     * Estimates with which weight aces should be treated.
     *
     * @return 1 or 11
     */
    public int aceWeight() {
        int sum = 0;
        int aceCounter = 0;
        for (Card card : this.cards) {
            if (card.weight != Constants.ACE_WEIGHT_DEFAULT) {
                sum += card.weight;
            } else {
                aceCounter += 1;
            }
        }

        if (aceCounter * Constants.ACE_WEIGHT_DEFAULT + sum > Constants.BLACKJACK_SCORE) {
            return Constants.ACE_WEIGHT_LOWERED;
        }
        return Constants.ACE_WEIGHT_DEFAULT;
    }
}
