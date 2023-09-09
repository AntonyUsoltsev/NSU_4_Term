package ru.nsu.ccfit.usoltsev.server;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.usoltsev.myUtils.CommandLoader;
import ru.nsu.ccfit.usoltsev.server.command.CommandServerExecutor;
import ru.nsu.ccfit.usoltsev.myUtils.Message;
import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

@Log4j2
public class Server implements Runnable, AutoCloseable {
    private static final String logOutMessage = " left the chat";
    private static final int TIMEOUT_TIME = 420_000;   // seven minutes in milliseconds
    private final Socket clientSocket;
    private final ObjectInputStream reader;
    private final CommandLoader commandLoader = new CommandLoader("ServerCommands.properties");
    @Setter
    @Getter
    private String userName;
    @Getter
    private final ObjectOutputStream writer;
    @Getter
    private final KernelServer kernelServer;

    @SneakyThrows
    public Server(final KernelServer kernelServer, final Socket clientSocket) throws IOException {
        this.kernelServer = kernelServer;
        this.clientSocket = clientSocket;
        this.clientSocket.setSoTimeout(TIMEOUT_TIME);
        this.reader = new ObjectInputStream(this.clientSocket.getInputStream());
        this.writer = new ObjectOutputStream(this.clientSocket.getOutputStream());
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message<?> message = (Message<?>) reader.readObject();
                log.info("Get message");
                System.out.println(message.getTypeMessage());
                CommandServerExecutor commandServerExecutor = commandLoader.createInstanceClass(message.getTypeMessage() + "ServerExecutor");
                if (commandServerExecutor != null)
                    commandServerExecutor.execute(this, message);
            } catch (SocketTimeoutException e) {
                kernelServer.broadCastListMessages(kernelServer.createMessageList("LogOut", userName + logOutMessage));
                kernelServer.broadCastListUsers();
                this.close();
            }  catch (IOException | ClassNotFoundException e) {
                log.info(userName + " logout");
                this.close();
                break;
            }
        }
    }

    public void SendMessage(final Message<?> message) {
        try {
            writer.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            clientSocket.close();
            writer.close();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void logOut() {
        this.kernelServer.deleteMember(this.userName);
        this.close();
    }
}
