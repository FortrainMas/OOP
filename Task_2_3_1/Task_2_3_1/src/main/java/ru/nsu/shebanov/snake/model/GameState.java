package ru.nsu.shebanov.snake.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static ru.nsu.shebanov.snake.model.GameState.GameObjects.*;

public class GameState {
    int APPLES_NUMBER = 5;
    int COD_NUMBER = 5;
    int INVERTERS_NUMBER = 5;
    int MINES_NUMBER = 5;

    public int applesCount = 0;
    public int codCount = 0;
    public int invertersCount = 0;
    public int minesCount = 0;

    public enum Directions {
        UP, DOWN, LEFT, RIGHT
    };
    public enum GameObjects {
        PLAYER, FIELD, APPLE, COD, MINE, INVERTER, FIELD2, FIELD3
    };
    public enum GameStatus {
        PLAY, LOST, WON
    };

    public int rows;
    public int cols;
    public GameObjects[][] field;

    public Directions direction;
    public GameStatus gameStatus;
    public int score;

    Snake player;
    List<Snake> snakes = new ArrayList<>();

    public GameState() {
        rows = 27;
        cols = 28;
        field = new GameObjects[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                field[i][j] = FIELD;
            }
        }

        player = new Snake(rows, cols, rows/2, cols/2, this);
        snakes.add(player);

        direction = Directions.RIGHT;
        gameStatus = GameStatus.PLAY;
        score = 0;
    }

    public void setUserDirection(Directions direction) {
        player.setDirection(direction);
    }

    private void validateSnakes () {
        snakes.replaceAll(Snake::move);

        snakes.removeIf(snake -> !snake.isAlive);

        for(var snake : snakes) {
            if (snake.isAlive) {
                for (var coordinate : snake.body) {
                    this.field[coordinate.x][coordinate.y] = PLAYER;
                }
            }
        }
    }

    public GameState nextState() {
        validateSnakes();

        List<Coordinates> freeCoordinates = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (field[i][j] == FIELD) {
                    freeCoordinates.add(new Coordinates(i, j));
                }
            }
        }

        Collections.shuffle(freeCoordinates);
        int pos = Math.min(5-applesCount, freeCoordinates.size());
        for(var coordinate : freeCoordinates.subList(0, pos)) {
            field[coordinate.x][coordinate.y] = APPLE;
            applesCount += 1;
        }
        for(var coordinate : freeCoordinates.subList(pos+1, pos + 1 + Math.min(5-codCount, freeCoordinates.size()))) {
            field[coordinate.x][coordinate.y] = COD;
            codCount += 1;
        }
        pos += Math.min(5-codCount, freeCoordinates.size()) + 2;
        for(var coordinate : freeCoordinates.subList(pos, pos + Math.min(5-invertersCount, freeCoordinates.size()))) {
            field[coordinate.x][coordinate.y] = INVERTER;
            invertersCount += 1;
        }
        pos += Math.min(5-invertersCount, freeCoordinates.size()) + 1;
        for(var coordinate : freeCoordinates.subList(pos, pos + Math.min(5-minesCount, freeCoordinates.size()))) {
            field[coordinate.x][coordinate.y] = MINE;
            minesCount += 1;
        }




        return this;
    }
}
