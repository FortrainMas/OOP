package ru.nsu.shebanov;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class BlackjackTest {

    @BeforeEach
    public void setUp(){
        
    }

    @Test
    public void testDealInitialCards() {
        Blackjack blackjack = new Blackjack();

        blackjack.dealInitialCards();

        // Assert
        // Check if the player and dealer have 2 cards each
        assertEquals(2, blackjack.getPlayerHand().size());
        assertEquals(2, blackjack.getDealerHand().size());
    }

    @Test
    public void testPlayerHit() {
        // Arrange
        Blackjack blackjack = new Blackjack();
        blackjack.dealInitialCards(); // Initialize hands

        // Act
        blackjack.playerHit();

        // Assert
        // Check if the player's hand size has increased by 1
        assertEquals(3, blackjack.getPlayerHand().size());
    }

    @Test
    public void testCalculateScore() {
        // Arrange
        Blackjack blackjack = new Blackjack();
        // Create a hand with specific cards for testing
        blackjack.getPlayerHand().add(new Card(Card.Rank.ACE, Card.Suit.HEARTS));
        blackjack.getPlayerHand().add(new Card(Card.Rank.KING, Card.Suit.DIAMONDS));

        // Act
        int score = blackjack.calculateScore(blackjack.getPlayerHand());

        // Assert
        assertEquals(21, score); // Ace + King = 21
    }

    @Test
    public void testDealerTurn() {
        // Arrange
        Blackjack blackjack = new Blackjack();
        // Set up the dealer's hand for testing purposes
        blackjack.getDealerHand().add(new Card(Card.Rank.TEN, Card.Suit.CLUBS));
        blackjack.getDealerHand().add(new Card(Card.Rank.SEVEN, Card.Suit.SPADES));

        // Act
        blackjack.dealerTurn();

        // Assert
        // Check if the dealer's score is at least 17 or they have busted
        assertTrue(blackjack.calculateScore(blackjack.getDealerHand()) >= 17 || blackjack.calculateScore(blackjack.getDealerHand()) > 21);
    }

    @Test
    public void testDetermineWinner() {
        // Arrange
        Blackjack blackjack = new Blackjack();
        // Set up player and dealer scores for different scenarios
        blackjack.setPlayerScore(18);
        blackjack.setDealerScore(17);

        // Act
        String winner = blackjack.determineWinner();

        // Assert
        assertEquals("Player", winner); // Player wins with 18 vs Dealer's 17
    }

    @Test
    public void testGameReset() {
        // Arrange
        Blackjack blackjack = new Blackjack();
        blackjack.dealInitialCards();
        blackjack.playerHit(); // Modify the game state

        // Act
        blackjack.resetGame();

        // Assert
        // Check if the player and dealer hands are empty and scores are reset
        assertEquals(0, blackjack.getPlayerHand().size());
        assertEquals(0, blackjack.getDealerHand().size());
        assertEquals(0, blackjack.getPlayerScore());
        assertEquals(0, blackjack.getDealerScore());
    }
}
