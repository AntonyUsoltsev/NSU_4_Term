package ru.nsu.ccfit.usoltsev.client.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.usoltsev.client.controller.Controller;
import ru.nsu.ccfit.usoltsev.myUtils.Message;

import java.util.List;

@Log4j2
public class LogInController {
    private static Controller controller;
    private int messageCount = 0;

    @FXML
    private TextField nameField;

    @FXML
    private TextField messageField;

    @FXML
    private ListView<Text> membersListView;

    @FXML
    private ListView<Text> messagesListView;

    @FXML
    private Text membersCount;

    public static void registrationController(Controller impController) {
        controller = impController;
    }


    public void updateMembers(final List<String> membersList) {
        log.info("update members");
        Platform.runLater(() -> {
            membersCount.setText(String.valueOf((long) membersList.size()));
            membersListView.getItems().clear();
            for (String member : membersList) {
                Text memberName = new Text(member);
                memberName.setFill(Color.WHITE);
                if (member.equals(controller.getUser().getUserName())) {
                    memberName.setFill(Color.AQUA);
                }
                membersListView.getItems().add(memberName);
            }
            membersListView.refresh();
        });
    }

    public void updateMessages(final List<Message<String>> messages) {
        log.info("update messages");
        Platform.runLater(() -> {
            messageCount += messages.size();
            for (var message : messages) {
                Text textMessage = new Text(message.getTypeMessage() + ":\n" + message.getContent());
                textMessage.setWrappingWidth(370);
                textMessage.setFill(Color.WHITE);
                if (message.getTypeMessage().equals("LogIn")) {
                    textMessage.setText(message.getContent());
                    textMessage.setFill(Color.AQUA);
                    textMessage.setTextAlignment(TextAlignment.CENTER);
                } else if (message.getTypeMessage().equals("LogOut")) {
                    textMessage.setText(message.getContent());
                    textMessage.setFill(Color.RED);
                    textMessage.setTextAlignment(TextAlignment.CENTER);
                }
                if (message.getTypeMessage().equals(controller.getUser().getUserName())) {
                    textMessage.setTextAlignment(TextAlignment.RIGHT);
                }

                messagesListView.getItems().add(textMessage);
            }
            messagesListView.refresh();
            messagesListView.scrollTo(messageCount);
        });
    }

    public void handleMessageEnterKeyPressed() {
        messageField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && (messageField.getText().length() != 0)) {
                controller.sendMessage(messageField.getText());
                messageField.clear();
            }
        });
    }

    public void logInButton() {
        controller.logInTry(nameField.getText());
    }

    public void handleLogInKeyPressed() {
        nameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && (nameField.getText().length() != 0)) {
                controller.logInTry(nameField.getText());
                nameField.clear();
            }
        });
    }
}
