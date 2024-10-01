package ru.nsu.shebanov.blackjack;

import java.util.ArrayList;

/**
 * Controls hand behaviour.
 */
public abstract class Hand {

    public static final int ACE_DEFAULT_WEIGHT = 11;
    public static final int ACE_LOWERED_WEIGHT = 1;
    public static final int BLACKJACK_SCORE = 21;

    public ArrayList<Card> cards;

    public Hand(Card card1, Card card2) {
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
            if (card.weight != ACE_DEFAULT_WEIGHT) {
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
            if (card.weight == ACE_DEFAULT_WEIGHT) {
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

        if (aceCounter * ACE_DEFAULT_WEIGHT + sum > BLACKJACK_SCORE) {
            return ACE_LOWERED_WEIGHT;
        }
        return ACE_DEFAULT_WEIGHT;
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
