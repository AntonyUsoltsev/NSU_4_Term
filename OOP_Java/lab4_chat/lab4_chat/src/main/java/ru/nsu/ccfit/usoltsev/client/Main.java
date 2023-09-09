package ru.nsu.ccfit.usoltsev.client;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.ccfit.usoltsev.client.controller.Controller;
import ru.nsu.ccfit.usoltsev.client.view.LogInController;
import ru.nsu.ccfit.usoltsev.client.view.View;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        View view = new View(stage);
        Controller controller = new Controller(view);
        LogInController.registrationController(controller);
    }
}
