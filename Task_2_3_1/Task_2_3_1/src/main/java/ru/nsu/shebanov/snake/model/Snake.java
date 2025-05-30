package ru.nsu.shebanov.snake.model;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static ru.nsu.shebanov.snake.model.GameState.GameObjects.*;
import static ru.nsu.shebanov.snake.model.GameState.GameObjects.MINE;

public class Snake {
    boolean isAlive;
    boolean eatApple;
    int onCodeine = 0;


    public Coordinates head;

    public int cols;
    public int rows;

    List<Coordinates> body = new LinkedList<>();
    GameState.Directions direction;
    boolean directionLock = false;

    private GameState gameState;

    public Snake(int rows, int cols, int headX, int headY, GameState gameState) {
        this.rows = rows;
        this.cols = cols;

        head = new Coordinates(headX, headY);

        body.add(new Coordinates(headX, headY));
        this.gameState = gameState;

        isAlive = true;
    }

    public void setDirection(GameState.Directions direction) {
        if ((this.direction == GameState.Directions.LEFT && direction == GameState.Directions.RIGHT) ||
                (this.direction == GameState.Directions.RIGHT && direction == GameState.Directions.LEFT) ||
                (this.direction == GameState.Directions.DOWN && direction == GameState.Directions.UP) ||
                (this.direction == GameState.Directions.UP && direction == GameState.Directions.DOWN) ||
                directionLock
        ) {
            return;
        }
        directionLock = true;
        this.direction = direction;
    }

    public void invert() {
        this.body = this.body.reversed();
        this.head = this.body.getFirst();

        if(this.direction == GameState.Directions.LEFT) {
            this.direction = GameState.Directions.RIGHT;
        } else if(this.direction == GameState.Directions.RIGHT) {
            this.direction = GameState.Directions.LEFT;
        } else if(this.direction == GameState.Directions.UP) {
            this.direction = GameState.Directions.DOWN;
        } else {
            this.direction = GameState.Directions.UP;
        }

    }

    public Snake move() {
        System.out.println("TRYNNA MOVE");
        GameState.Directions direction = this.direction;
        if(onCodeine > 0) {
            onCodeine -= 1;
            GameState.Directions[] values = GameState.Directions.values();
            do {
                direction = values[new Random().nextInt(values.length)];
            } while ((direction == GameState.Directions.LEFT && this.direction == GameState.Directions.RIGHT) ||
                    (direction == GameState.Directions.RIGHT && this.direction == GameState.Directions.LEFT) ||
                    (direction == GameState.Directions.UP && this.direction == GameState.Directions.DOWN) ||
                    (direction == GameState.Directions.DOWN && this.direction == GameState.Directions.UP));

        }
        directionLock = false;
        Coordinates head = body.getFirst();
        Coordinates newHead = head;

        if(direction == GameState.Directions.LEFT) {
            if (head.y - 1 < 0) {
                isAlive = false;
            } else {
                newHead = new Coordinates(head.x, head.y -1);
            }
        } else if(direction == GameState.Directions.RIGHT) {
            if(head.y + 1 >= cols) {
                isAlive = false;
            } else {
                newHead = new Coordinates(head.x, head.y + 1);
            }
        } else if(direction == GameState.Directions.UP) {
            if (head.x - 1 < 0) {
                isAlive = false;
            } else {
                newHead = new Coordinates(head.x - 1, head.y);
            }
        } else if(direction == GameState.Directions.DOWN){
            if(head.x + 1 >= rows) {
                isAlive = false;
            } else {
                newHead = new Coordinates(head.x + 1, head.y);
            }
        } else{
            return this;
        }


        body.addFirst(newHead);
        this.head = newHead;





        var field = gameState.field;

        if (field[this.head.x][this.head.y] == APPLE) {
            this.eatApple = true;
            gameState.applesCount -= 1;
            field[this.head.x][this.head.y] = PLAYER;
        } else {
            var last = body.removeLast();
            field[last.x][last.y] = FIELD;
        }
        if (field[this.head.x][this.head.y] == COD) {
            this.onCodeine = 5;
            gameState.codCount -=1;
            field[this.head.x][this.head.y] = PLAYER;
        }
        else if (field[this.head.x][this.head.y] == INVERTER) {
            this.invert();
            gameState.invertersCount -= 1;
            field[this.head.x][this.head.y] = PLAYER;
        }
        else if (field[this.head.x][this.head.y] == MINE) {
            this.isAlive = false;
            gameState.minesCount -= 1;
            field[this.head.x][this.head.y] = PLAYER;
        }

        for(var snake : gameState.snakes) {
            for(var coordinate : snake.body.subList(1, snake.body.size())) {
                if(coordinate.x == this.body.getFirst().x && coordinate.y == this.body.getFirst().y) {
                    this.isAlive = false;
                }
            }
        }

        return this;
    }
}
