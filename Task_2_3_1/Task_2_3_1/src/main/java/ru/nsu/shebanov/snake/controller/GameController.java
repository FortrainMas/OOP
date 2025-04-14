package ru.nsu.shebanov.snake.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import ru.nsu.shebanov.snake.model.GameState;

public class GameController {
    @FXML
    private GridPane grid;

    private int rows;
    private int cols;
    private Rectangle[][] squares;
    GameState gameState = new GameState();

    public void initialize() {
        rows = gameState.rows;
        cols = gameState.cols;

        squares  = new Rectangle[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Rectangle square = new Rectangle(30, 30, Color.BLACK);
                grid.add(square, col, row);
                squares[row][col] = square;
            }
        }

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.05), e -> toggleSquares(gameState))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void toggleSquares(GameState gameState) {
        GameState gs = gameState.nextState();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Rectangle square = squares[row][col];


                if(gs.field[row][col] == GameState.GameObjects.FIELD) {
                    square.setFill(Color.GREEN);
                } else if(gs.field[row][col] == GameState.GameObjects.APPLE) {
                    square.setFill(Color.RED);
                } else if(gs.field[row][col] == GameState.GameObjects.PLAYER) {
                    square.setFill(Color.AQUA);
                } else if(gs.field[row][col] == GameState.GameObjects.COD) {
                    square.setFill(Color.PURPLE);
                } else if(gs.field[row][col] == GameState.GameObjects.MINE) {
                    square.setFill(Color.BLACK);
                } else if(gs.field[row][col] == GameState.GameObjects.INVERTER) {
                    square.setFill(Color.BISQUE);
                }
            }
        }
    }

    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP -> gameState.setUserDirection(GameState.Directions.UP);
            case DOWN -> gameState.setUserDirection(GameState.Directions.DOWN);
            case LEFT -> gameState.setUserDirection(GameState.Directions.LEFT);
            case RIGHT -> gameState.setUserDirection(GameState.Directions.RIGHT);
            case R ->  gameState = new GameState();
        }
    }

}
