package ru.nsu.shebanov;


import java.util.Scanner;

public class Blackjack {
    private static Deck deck;
    private static int gameRound = 1;
    private static int playerGameScore = 0;
    private static int dealerGameScore = 0;
    private static Scanner scanner;
    private static UserHand userHand;
    private static DealerHand dealerHand;

    static void playerTurn(){
        System.out.println("Ваш ход");
        System.out.println("-------");
        System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться...");

        int feedback = scanner.nextInt();
        while(feedback == 1){
            Card newCard = deck.getCard();
            userHand.add_card(newCard);
            System.out.println("Вы открыли карту " + newCard.toString());
            System.out.println(userHand.toString());
            System.out.println(dealerHand.toString() + "\n");

            if (userHand.count_cards() >= 21){
                return;
            }

            System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться...");

            feedback = scanner.nextInt();
        }
    }

    static void dealerTurn(){
        dealerHand.handRevealed = true;
        System.out.println("Дилер открывает закрытую карту " + dealerHand.cards.get(1));
        System.out.println(userHand.toString());
        System.out.println(dealerHand.toString() + "\n");

        while(dealerHand.count_cards() < 17){
            Card newCard = deck.getCard();
            dealerHand.add_card(newCard);
            System.out.println("Дилер открывает карту " + newCard.toString());
            System.out.println(userHand.toString());
            System.out.println(dealerHand.toString() + "\n");
        }


    }

    private static boolean results(boolean forcedFinish){
        int userScore = userHand.count_cards();
        int dealerScore = dealerHand.count_cards();

        if(userScore == 21 || (forcedFinish && userScore > dealerScore && userScore < 22) || dealerScore > 21){
            playerGameScore += 1;
            System.out.println("Вы выиграли раунд! Счет "+playerGameScore+":"+dealerGameScore);
            return true;
        }
        if((dealerHand.handRevealed && dealerScore == 21) || (forcedFinish && userScore < dealerScore) || userScore > 21){
            dealerGameScore += 1;
            System.out.println("Раунд выиграл дилер! Счет "+playerGameScore+":"+dealerGameScore);
            return true;
        }
        if(forcedFinish){
            System.out.println("Ничья! Счет "+playerGameScore+":"+dealerGameScore);
            return true;
        }
        return false;
    }

    public static void round(){
        userHand = new UserHand(deck.getCard(), deck.getCard());
        dealerHand = new DealerHand(deck.getCard(), deck.getCard());

        System.out.println("Раунд " + gameRound);
        System.out.println("Дилер раздал карты");
        System.out.println(userHand.toString());
        System.out.println(dealerHand.toString() + "\n");

        boolean forcedFinishCondition;
        boolean isFinished;
        while (true){
            playerTurn();
            forcedFinishCondition = dealerHand.handRevealed && (dealerHand.count_cards() >= 17);
            isFinished = results(forcedFinishCondition);
            if (isFinished){
                break;
            }
            dealerTurn();
            isFinished = results(false);
            if (isFinished){
                break;
            }

        }
    }

    public static void main(String[] args) {
        deck = new Deck();
        scanner = new Scanner(System.in);


        for(int i =0; i < 100; i++){
            round();
            gameRound+=1;
        }

        scanner.close();
    }
}
