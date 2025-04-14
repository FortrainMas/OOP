package ru.nsu.shebanov.snake.model;

import javafx.scene.shape.Rectangle;

import java.util.Random;

public class GameModel {
    private int score = 0;
    private GameState gameState;



    public static void placeRandomTwo(int[][] array) {
        Random random = new Random();
        int rows = array.length;
        int cols = array[0].length;

        int x, y;
        do {
            x = random.nextInt(rows);
            y = random.nextInt(cols);
        } while (array[x][y] == 1);

        array[x][y] = 2;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        score++;
    }
}
