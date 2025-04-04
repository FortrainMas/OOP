package ru.nsu.shebanov.snake.model;


import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static ru.nsu.shebanov.snake.model.GameState.GameObjects.APPLE;
import static ru.nsu.shebanov.snake.model.GameState.GameObjects.PLAYER;

public class Snake {
    boolean isAlive;
    boolean eatApple;

    public Coordinates head;

    public int cols;
    public int rows;

    List<Coordinates> body = new LinkedList<>();
    GameState.Directions direction;

    public Snake(int rows, int cols, int headX, int headY, GameState.Directions direction) {
        this.rows = rows;
        this.cols = cols;

        head = new Coordinates(headX, headY);
        this.direction = direction;

        body.add(new Coordinates(headX, headY));

        isAlive = true;
    }

    public Snake move() {
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
        } else {
            if(head.x + 1 >= rows) {
                isAlive = false;
            } else {
                newHead = new Coordinates(head.x + 1, head.y);
            }
        }


        body.addFirst(newHead);
        this.head = newHead;
        if(!eatApple){
            body.removeLast();
        }else {
            eatApple = false;
        }
        return this;
    }
}
