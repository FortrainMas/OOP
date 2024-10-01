package ru.nsu.shebanov.blackjack;

import java.util.Scanner;

/**
 * Class which runs the game.
 */
public class Blackjack {
    public Deck deck;
    public Scanner scanner;

    public static final int BLACKJACK_SCORE = 21;
    public static final int DEALER_STOP_SCORE = 17;

    private int gameRound = 1;
    private int playerGameScore = 0;
    private int dealerGameScore = 0;
    private UserHand userHand;
    private DealerHand dealerHand;

    /**
     * Behaviour of the game when it is user turn.
     *
     * @return if user won or lost the game on his turn
     */
    private boolean playerTurn() {
        if (checkBlackjack()) {
            return true;
        }

        System.out.println("Ваш ход");
        System.out.println("-------");
        System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться...");

        int feedback = scanner.nextInt();
        while (feedback == 1) {
            Card newCard = deck.getCard();
            userHand.addCard(newCard);
            System.out.println("Вы открыли карту " + newCard.toString());
            System.out.println(userHand.toString());
            System.out.println(dealerHand.toString() + "\n");

            if (userHand.countCards() > 21) {
                return results(false);
            }

            System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться...");

            feedback = scanner.nextInt();
        }
        return results(false);
    }

    /**
     * Behaviour of the game when it is dealer turn.
     *
     * @return if dealer won or lost the game on his turn
     */
    private boolean dealerTurn() {
        dealerHand.handRevealed = true;
        System.out.println("Дилер открывает закрытую карту " + dealerHand.cards.get(1));
        System.out.println(userHand.toString());
        System.out.println(dealerHand.toString() + "\n");

        if (checkBlackjack()) {
            return true;
        }

        while (dealerHand.countCards() < DEALER_STOP_SCORE) {
            Card newCard = deck.getCard();
            dealerHand.addCard(newCard);
            System.out.println("Дилер открывает карту " + newCard.toString());
            System.out.println(userHand.toString());
            System.out.println(dealerHand.toString() + "\n");
        }

        return results(true);
    }

    /**
     * Checks for blackjack.
     * if dealerHand revealed works for dealer
     * otherwise for player
     *
     * @return did some player one the game
     */
    private boolean checkBlackjack() {
        if (dealerHand.handRevealed) {
            if (dealerHand.countCards() == BLACKJACK_SCORE) {
                dealerGameScore += 1;
                System.out.println(
                        "Раунд выиграл дилер! Счет " + playerGameScore + ":" + dealerGameScore);
                return true;
            }
        } else {
            if (userHand.countCards() == BLACKJACK_SCORE) {
                playerGameScore += 1;
                System.out.println(
                        "Вы выиграли раунд! Счет " + playerGameScore + ":" + dealerGameScore);
                return true;
            }
        }
        return false;
    }

    /**
     * Check for game results.
     *
     * @param forcedFinish should the game be finished even if both players have <21
     *
     * @return results of the game
     */
    private boolean results(boolean forcedFinish) {
        int userScore = userHand.countCards();
        int dealerScore = dealerHand.countCards();

        if ((forcedFinish && userScore > dealerScore && userScore < BLACKJACK_SCORE + 1)
                || dealerScore > BLACKJACK_SCORE) {
            playerGameScore += 1;
            System.out.println(
                    "Вы выиграли раунд! Счет " + playerGameScore + ":" + dealerGameScore);
            return true;
        }
        if ((dealerHand.handRevealed && dealerScore == BLACKJACK_SCORE)
                || (forcedFinish && userScore < dealerScore)
                || userScore > BLACKJACK_SCORE) {
            dealerGameScore += 1;
            System.out.println(
                    "Раунд выиграл дилер! Счет " + playerGameScore + ":" + dealerGameScore);
            return true;
        }
        if (forcedFinish) {
            System.out.println("Ничья! Счет " + playerGameScore + ":" + dealerGameScore);
            return true;
        }
        return false;
    }

    /**
     * Controls order of action in game round.
     */
    public void round() {
        userHand = new UserHand(deck.getCard(), deck.getCard());
        dealerHand = new DealerHand(deck.getCard(), deck.getCard());

        System.out.println("Раунд " + gameRound);
        System.out.println("Дилер раздал карты");
        System.out.println(userHand.toString());
        System.out.println(dealerHand.toString() + "\n");

        boolean isFinished;

        isFinished = playerTurn();
        if (!isFinished) {
            dealerTurn();
        }

        gameRound += 1;
    }

    /**
     * initialize everything and controls rounds.
     */
    public static void main(String[] args) {
        final var blackjack = new Blackjack();
        blackjack.deck = new Deck();
        blackjack.scanner = new Scanner(System.in);

        for (int i = 0; i < 100; i++) {
            blackjack.round();
        }

        blackjack.scanner.close();
    }
}
