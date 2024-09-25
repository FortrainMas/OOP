package ru.nsu.shebanov.blackjack;

import java.util.ArrayList;

/**
 * Controls hand behaviour.
 */
public abstract class Hand {
    private final int aceDefaultWeight = 11;
    private final int aceLoweredWeight = 1;
    private final int blackJackScore = 21;

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
     * Count sum without aces.
     *
     * @return sum of cards without aces
     */
    private int countAcelessSum() {
        int sum = 0;
        for (Card card : this.cards) {
            if (card.weight != aceDefaultWeight) {
                sum += card.weight;
            }
        }

        return sum;
    }

    /**
     * Count number of aces in the deck.
     *
     * @return number of aces in the deck
     */
    private int countAces() {
        int countAces = 0;
        for (Card card : this.cards) {
            if (card.weight == aceDefaultWeight) {
                countAces += 1;
            }
        }

        return countAces;
    }

    /**
     * Estimates with which weight aces should be treated.
     *
     * @return 1 or 11
     */
    public int aceWeight() {
        int sum = countAcelessSum();
        int aceCounter = countAces();

        if (aceCounter * aceDefaultWeight + sum > blackJackScore) {
            return aceLoweredWeight;
        }
        return aceDefaultWeight;
    }


    /**
     * Counts score of cards in hand.
     *
     * @return score
     */
    public int countCards() {
        int sum = countAcelessSum();
        int aceCounter = countAces();

        sum += aceWeight() * aceCounter;

        return sum;
    }


}
