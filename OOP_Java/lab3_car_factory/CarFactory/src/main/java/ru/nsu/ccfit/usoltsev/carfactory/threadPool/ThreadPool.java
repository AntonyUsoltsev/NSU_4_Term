package ru.nsu.ccfit.usoltsev.carfactory.threadPool;

import ru.nsu.ccfit.usoltsev.carfactory.FactoryProperties;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    public ExecutorService executorService;

    public ThreadPool() {
        this.executorService = Executors.newFixedThreadPool(FactoryProperties.NUMBER_OF_WORKERS);
    }

    public void killThreadPool() {
        executorService.shutdown();
    }
}
