package ru.nsu.shebanov;

import java.util.Scanner;

/**
 * Class which runs the game.
 */
public class Blackjack {
    private static Deck deck;
    private static int gameRound = 1;
    private static int playerGameScore = 0;
    private static int dealerGameScore = 0;
    private static Scanner scanner;
    private static UserHand userHand;
    private static DealerHand dealerHand;

    /**
     * Behaviour of the game when it is user turn.
     *
     * @return if user won or lost the game on his turn
     */
    static boolean playerTurn() {
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

            if (userHand.countCards() > Constants.BLACKJACK_SCORE) {
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
    static boolean dealerTurn() {
        dealerHand.handRevealed = true;
        System.out.println("Дилер открывает закрытую карту " + dealerHand.cards.get(1));
        System.out.println(userHand.toString());
        System.out.println(dealerHand.toString() + "\n");

        if (checkBlackjack()) {
            return true;
        }

        while (dealerHand.countCards() < Constants.DEALER_STOP_SCORE) {
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
    private static boolean checkBlackjack() {
        if (dealerHand.handRevealed) {
            if (dealerHand.countCards() == Constants.BLACKJACK_SCORE) {
                dealerGameScore += 1;
                System.out.println(
                        "Раунд выиграл дилер! Счет " + playerGameScore + ":" + dealerGameScore);
                return true;
            }
        } else {
            if (userHand.countCards() == Constants.BLACKJACK_SCORE) {
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
    private static boolean results(boolean forcedFinish) {
        int userScore = userHand.countCards();
        int dealerScore = dealerHand.countCards();

        if ((forcedFinish && userScore > dealerScore && userScore < Constants.BLACKJACK_SCORE + 1)
                || dealerScore > Constants.BLACKJACK_SCORE) {
            playerGameScore += 1;
            System.out.println(
                    "Вы выиграли раунд! Счет " + playerGameScore + ":" + dealerGameScore);
            return true;
        }
        if ((dealerHand.handRevealed && dealerScore == Constants.BLACKJACK_SCORE)
                || (forcedFinish && userScore < dealerScore)
                || userScore > Constants.BLACKJACK_SCORE) {
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
    public static void round() {
        userHand = new UserHand(deck.getCard(), deck.getCard());
        dealerHand = new DealerHand(deck.getCard(), deck.getCard());

        System.out.println("Раунд " + gameRound);
        System.out.println("Дилер раздал карты");
        System.out.println(userHand.toString());
        System.out.println(dealerHand.toString() + "\n");

        boolean forcedFinishCondition;
        boolean isFinished;

        isFinished = playerTurn();
        if (!isFinished) {
            dealerTurn();
        }
    }

    /**
     * initialize everything and controls rounds.
     */
    public static void main(String[] args) {
        deck = new Deck();
        scanner = new Scanner(System.in);

        for (int i = 0; i < Constants.ROUNDS_NUMBER; i++) {
            round();
            gameRound += 1;
        }

        scanner.close();
    }
}
