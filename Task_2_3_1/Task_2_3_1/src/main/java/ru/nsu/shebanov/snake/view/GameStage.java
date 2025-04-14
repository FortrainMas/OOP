package ru.nsu.shebanov.snake.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.shebanov.snake.controller.GameController;

import java.io.IOException;

public class GameStage {
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ru/nsu/shebanov/snake/Game.fxml"));
        Parent root = fxmlLoader.load();
        GameController controller = fxmlLoader.getController();
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(controller::handleKeyPress);
        stage.setTitle("Snake Game");
        stage.setScene(scene);
        stage.show();
    }
}
