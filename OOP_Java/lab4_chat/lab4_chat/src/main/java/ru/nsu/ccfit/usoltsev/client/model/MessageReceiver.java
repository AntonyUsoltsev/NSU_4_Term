package ru.nsu.ccfit.usoltsev.client.model;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.usoltsev.client.controller.Controller;
import ru.nsu.ccfit.usoltsev.myUtils.Message;

import java.io.IOException;
import java.io.ObjectInputStream;

@Log4j2
public class MessageReceiver implements Runnable {
    @Setter
    Controller controller;
    @Setter
    ObjectInputStream reader;

    @Override
    public void run() {
        while (controller.getUser().isSocketAlive()) {
            try {
                controller.handleMessage((Message<?>) reader.readObject());
            } catch (IOException | ClassNotFoundException e) {
                Thread.currentThread().interrupt();
                log.error("thread is interrupt");
                return;
            }
            log.info("Get message");
        }
    }
}
