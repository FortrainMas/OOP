package ru.nsu.shebanov.snake.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ru.nsu.shebanov.snake.model.GameModel;

public class MainController {
    private final GameModel model = new GameModel();

    @FXML
    private Label scoreLabel;

    @FXML
    private void increaseScore() {
        model.increaseScore();
        scoreLabel.setText("Очки: " + model.getScore());
    }
}
