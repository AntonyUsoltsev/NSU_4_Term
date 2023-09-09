package ru.nsu.ccfit.usoltsev.client.model;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.usoltsev.client.controller.Controller;
import ru.nsu.ccfit.usoltsev.myUtils.Message;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

@Log4j2
public class User implements Closeable {
    private static final String host = "localhost";
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private final Controller controller;
    private Socket socket;
    @Getter
    @Setter
    private String userName;
    @Getter
    private final UUID id;

    public User(Controller controller) {
        id = UUID.randomUUID();
        this.controller = controller;
    }

    public void connectToServer() {
        try {
            socket = new Socket(host, 8080);
            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            socket.close();
            writer.close();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Message registration(final String name) throws IOException {
        writer.writeObject(new Message<>("Login", name));
        try {
            return (Message<?>) reader.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public void sendMessageOnServer(final String userMessage) {
        writer.writeObject(new Message<>("Message", userMessage));
    }

    @SneakyThrows
    public void logOut() {
        if (socket.isConnected()) {
            writer.writeObject(new Message<>("LogOut", ""));
        }
    }

    public void run() {
        MessageReceiver messageReceiver = new MessageReceiver();
        messageReceiver.setReader(reader);
        messageReceiver.setController(controller);
        Thread thread = new Thread(messageReceiver);
        thread.start();
    }

    public boolean isSocketAlive() {
        return socket.isConnected();
    }
}
