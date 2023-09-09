package nsu.fit.usoltsev.pacmangamenew;

import javafx.stage.Stage;
import nsu.fit.usoltsev.pacmangamenew.Control.PacManController;
import nsu.fit.usoltsev.pacmangamenew.View.WindowView;
public class Main extends javafx.application.Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        WindowView.setWindowOptions(stage);
        PacManController.stage = stage;
        PacManController.newMenu();
        stage.setScene(PacManController.scene);
        stage.setResizable(false);
        stage.show();
    }
}