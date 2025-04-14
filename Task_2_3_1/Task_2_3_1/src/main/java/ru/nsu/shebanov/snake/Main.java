package ru.nsu.shebanov.snake;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.shebanov.snake.view.GameStage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        new GameStage().start(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
