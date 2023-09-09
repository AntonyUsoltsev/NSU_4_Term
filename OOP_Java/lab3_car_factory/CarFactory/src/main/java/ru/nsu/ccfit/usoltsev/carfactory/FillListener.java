package ru.nsu.ccfit.usoltsev.carfactory;

import java.util.concurrent.atomic.AtomicInteger;

public interface FillListener {
    void updateFill(AtomicInteger fill, String clazz);
}
