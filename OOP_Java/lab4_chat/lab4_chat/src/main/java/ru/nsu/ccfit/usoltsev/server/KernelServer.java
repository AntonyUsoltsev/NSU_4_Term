package ru.nsu.ccfit.usoltsev.server;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.usoltsev.myUtils.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

@Log4j2
public class KernelServer {
    private final Map<String, Server> serversAlive = new HashMap<>();
    private final List<Message<String>> messagesHistory = new LinkedList<>();
    private final ServerSocket serverSocket;

    public KernelServer() throws IOException {
        log.info("Create kernel server");
        serverSocket = new ServerSocket(8080);
    }

    public void start() {
        Socket clientSocket;
        log.info("Kernel server is start working");
        while (!serverSocket.isClosed()) {
            try {
                clientSocket = serverSocket.accept();
                log.info("Add new user: " + clientSocket.getPort());
                Thread thread = new Thread(new Server(this, clientSocket));
                thread.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean containsName(String name) {
        synchronized (serversAlive) {
            return serversAlive.containsKey(name);
        }
    }

    public void addServer(String name, Server serverCommunication) {
        synchronized (serversAlive) {
            serversAlive.put(name, serverCommunication);
            broadCastListUsers();
            broadCastMessagesHistory(serverCommunication);
            broadCastListMessages(createMessageList("LogIn", name + " entered the chat"));
        }
    }

    public void broadCastListUsers() {
        log.info("Broadcast users list");
        final Message<List<String>> messageMemberUP = new Message<>("MembersUpdate", new LinkedList<>(serversAlive.keySet()));
        for (Server server : serversAlive.values()) {
            server.SendMessage(messageMemberUP);
        }
    }

    public void addMessage(final String name, final String userMessage) {
        synchronized (messagesHistory) {
            broadCastListMessages(createMessageList(name, userMessage));
        }
    }

    public List<Message<String>> createMessageList(final String name, final String userMessage) {
        Message<String> message = new Message<>(name, userMessage);
        messagesHistory.add(message);
        List<Message<String>> messageList = new LinkedList<>();
        messageList.add(message);
        return messageList;
    }

    public void broadCastListMessages(List<Message<String>> messagesList) {
        log.info("Broadcast list messages");
        final Message<List<Message<String>>> message = new Message<>("Message", messagesList);
        for (Server server : serversAlive.values()) {
            server.SendMessage(message);
        }
    }

    private void broadCastMessagesHistory(@NotNull Server server) {
        server.SendMessage(new Message<>("Message", messagesHistory));
    }

    public void deleteMember(final String userName) {
        synchronized (serversAlive) {
            serversAlive.remove(userName);
            broadCastListMessages(createMessageList("LogOut", userName + " left the chat"));
            broadCastListUsers();
        }
    }
}
