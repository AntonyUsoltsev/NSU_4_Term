package ru.nsu.ccfit.usoltsev.client.controller;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.usoltsev.client.commands.CommandClientExecutor;
import ru.nsu.ccfit.usoltsev.myUtils.CommandLoader;
import ru.nsu.ccfit.usoltsev.client.model.User;
import ru.nsu.ccfit.usoltsev.client.view.View;
import ru.nsu.ccfit.usoltsev.myUtils.Message;

import java.io.IOException;

@Log4j2
public class Controller {
    @Getter
    private final View view;
    @Getter
    private final User user;
    CommandLoader commandLoader;

    @SneakyThrows
    public Controller(View view) {
        this.view = view;
        commandLoader = new CommandLoader("ClientCommands.properties");
        view.setController(this);
        view.generateLogIn();
        user = new User(this);
        user.connectToServer();
    }

    public void logInTry(final String name) {
        this.user.setUserName(name);
        Message serverMessage;
        try {
            serverMessage = user.registration(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Server answer " + serverMessage);
        handleMessage(serverMessage);
    }

    public void logOut() {
        user.logOut();
        user.close();
    }

    public synchronized void handleMessage(Message<?> message) {
        log.info("Message type: " + message.getTypeMessage() + ", message content " + message.getContent());
        CommandClientExecutor commandExecutor = commandLoader.createInstanceClass(message.getTypeMessage() + "ClientExecutor");
        commandExecutor.execute(this, message);
    }

    public void sendMessage(final String userMessage) {
        user.sendMessageOnServer(userMessage);
    }
}
