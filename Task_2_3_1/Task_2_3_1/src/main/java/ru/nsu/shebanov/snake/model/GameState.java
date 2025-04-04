package ru.nsu.shebanov.snake.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static ru.nsu.shebanov.snake.model.GameState.GameObjects.*;

public class GameState {
    public enum Directions {
        UP, DOWN, LEFT, RIGHT
    };
    public enum GameObjects {
        PLAYER, FIELD, APPLE
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

    List<Snake> snakes = new ArrayList<>();

    public GameState() {
        rows = 17;
        cols = 18;
        field = new GameObjects[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                field[i][j] = FIELD;
            }
        }


        snakes.add(new Snake(rows, cols, rows/2, cols/2, Directions.RIGHT));

        direction = Directions.RIGHT;
        gameStatus = GameStatus.PLAY;
        score = 0;
    }

    public void setUserDirection(Directions direction) {
        snakes.getFirst().direction = direction;
    }

    public GameState nextState() {
        snakes.replaceAll(Snake::move);

        for(int i = 0; i < snakes.size(); i++){
            Snake snake = snakes.get(i);
            for(int j = i; j < snakes.size(); j++) {
                if (snakes.get(j).body.contains(snake.head)) {
                    snake.isAlive = false;
                    break;
                }
            }
        }

        //snakes.removeIf(snake -> !snake.isAlive);

        for(var snake:snakes) {
            if (field[snake.head.x][snake.head.y] == APPLE) {
                snake.eatApple = true;
            }
        }

        int countApples = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (field[i][j] == APPLE) {
                    field[i][j] = APPLE;
                    countApples += 1;
                } else {
                    field[i][j] = FIELD;
                }
            }
        }

        for(var snake:snakes) {
            System.out.println(snake.head.x);
            if (field[snake.head.x][snake.head.y] == APPLE) {
                snakes.getFirst().eatApple = true;
                countApples -= 1;
            }

            for(var coordinate :snake.body) {
                field[coordinate.x][coordinate.y] = PLAYER;
            }
        }

        List<Coordinates> freeCoordinates = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (field[i][j] == FIELD) {
                    freeCoordinates.add(new Coordinates(i, j));
                }
            }
        }

        Collections.shuffle(freeCoordinates);
        for(var coordinate : freeCoordinates.subList(0, Math.min(5-countApples, freeCoordinates.size()))) {
            field[coordinate.x][coordinate.y] = APPLE;
        }

        return this;
    }
}
