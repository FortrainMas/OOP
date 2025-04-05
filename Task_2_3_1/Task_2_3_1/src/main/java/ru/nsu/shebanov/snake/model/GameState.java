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

        player = new Snake(rows, cols, rows/2, cols/2, Directions.RIGHT);
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

        for(int i = 0; i < snakes.size(); i++){
            Snake snake = snakes.get(i);
            for(int j = i; j < snakes.size(); j++) {
                if ( snakes.get(j).body.subList(1, snakes.get(j).body.size()).contains(snake.head)) {
                    snake.isAlive = false;
                    break;
                }
            }
        }

        snakes.removeIf(snake -> !snake.isAlive);

        for(var snake:snakes) {
            if (field[snake.head.x][snake.head.y] == APPLE) {
                snake.eatApple = true;
            }
            if (field[snake.head.x][snake.head.y] == COD) {
                snake.onCodeine = 5;
            }
            if (field[snake.head.x][snake.head.y] == INVERTER) {
                snake.invert();
            }
            if (field[snake.head.x][snake.head.y] == MINE) {
                snake.isAlive = false;
            }
        }
    }

    public GameState nextState() {
        validateSnakes();

        int countApples = 0;
        int countCod = 0;
        int countInverters = 0;
        int countMines = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (field[i][j] == APPLE) {
                    field[i][j] = APPLE;
                    countApples += 1;
                } else if(field[i][j] == COD) {
                    countCod += 1;
                } else if(field[i][j] == MINE) {
                    countMines += 1;
                } else if(field[i][j] == INVERTER) {
                    countInverters += 1;
                } else {
                    field[i][j] = FIELD;
                }
            }
        }

        for(var snake:snakes) {
            if(field[snake.head.x][snake.head.y] == APPLE) {
                countApples -= 1;
            } else if (field[snake.head.x][snake.head.y] == MINE) {
                countMines -= 1;
            } else if (field[snake.head.x][snake.head.y] == COD) {
                countCod -= 1;
            } else if(field[snake.head.x][snake.head.y] == INVERTER) {
                countInverters -= 1;
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
        for(var coordinate : freeCoordinates.subList(0, Math.min(5-countCod, freeCoordinates.size()))) {
            field[coordinate.x][coordinate.y] = COD;
        }
        for(var coordinate : freeCoordinates.subList(0, Math.min(5-countInverters, freeCoordinates.size()))) {
            field[coordinate.x][coordinate.y] = INVERTER;
        }
        for(var coordinate : freeCoordinates.subList(0, Math.min(5-countMines, freeCoordinates.size()))) {
            field[coordinate.x][coordinate.y] = MINE;
        }


        return this;
    }
}
