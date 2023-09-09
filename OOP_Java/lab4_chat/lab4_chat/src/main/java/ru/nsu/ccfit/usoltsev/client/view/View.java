package ru.nsu.ccfit.usoltsev.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.usoltsev.client.controller.Controller;
import ru.nsu.ccfit.usoltsev.myUtils.Message;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class View {
    private final Stage stage;
    private Pane root;
    private final Text error = new Text(100, 460, "");
    private LogInController logInController;
    @Setter
    private Controller controller;

    public View(final @NotNull Stage stage) {
        this.stage = stage;
        error.setFont(Font.font("System", 15));
        error.setStyle("-fx-fill: red;");
        stage.setOnCloseRequest((WindowEvent event) -> {
            this.controller.logOut();
            this.stage.close();
        });
    }

    public void generateLogIn() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new File("D:\\Antony\\NSU_Education\\4_Term\\OOP_Java\\lab4_chat\\lab4_chat\\src\\main\\resources\\client\\LogIn.fxml").toURI().toURL());
        root = fxmlLoader.load();
        root.getChildren().add(error);

        Scene scene = new Scene(root);
        stage.setTitle("Chat");
        stage.getIcons().add(new Image("D:\\Antony\\NSU_Education\\4_Term\\OOP_Java\\lab4_chat\\lab4_chat\\src\\main\\resources\\client\\chat.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void printErrorMessage(String error) {
        this.error.setText(error);
    }

    public void updateMembers(final List<String> membersList) {
        logInController.updateMembers(membersList);
    }

    public void printMessages(final @NotNull Message<List<Message<String>>> messages) {
        logInController.updateMessages(messages.getContent());
    }

    public void swapToChat() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new File("D:\\Antony\\NSU_Education\\4_Term\\OOP_Java\\lab4_chat\\lab4_chat\\src\\main\\resources\\client\\chat.fxml").toURI().toURL());
        root = fxmlLoader.load();
        logInController = fxmlLoader.getController();
        stage.setScene(new Scene(root));
    }
}
