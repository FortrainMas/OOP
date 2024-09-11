package ru.nsu.shebanov;

import java.util.ArrayList;

public abstract class Hand {
    public ArrayList<Card> cards;

    Hand(Card card1, Card card2) {
        this.cards = new ArrayList<Card>();
        this.cards.add(card1);
        this.cards.add(card2);
    }

    public void add_card(Card card) {
        this.cards.add(card);
    }

    public int count_cards() {
        int sum = 0;
        int aceCounter = 0;
        for (Card card : this.cards) {
            if (card.weight != 11) {
                sum += card.weight;
            }else{
                aceCounter += 1;
            }
        }

        if (aceCounter >= 1) {
            if (sum + 11 + aceCounter - 1 <= 21) {
                sum += 11 + aceCounter - 1;
            }else{
                sum += aceCounter;
            }
        }
        return sum;
    }

    public int ace_11_counter(){
        int sum = 0;
        int aceCounter = 0;
        for (Card card : this.cards) {
            if (card.weight != 11) {
                sum += card.weight;
            }else{
                aceCounter += 1;
            }
        }

        if (aceCounter >= 1) {
            if (sum + 11 + aceCounter - 1 <= 21) {
                return 1;
            }
        }
        return 0;
    }
}

