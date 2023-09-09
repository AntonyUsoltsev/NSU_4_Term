package ru.nsu.ccfit.usoltsev.server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            KernelServer kernelServer = new KernelServer();
            kernelServer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
