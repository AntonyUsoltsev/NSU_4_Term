package ru.nsu.ccfit.usoltsev.carfactory.producers;

import ru.nsu.ccfit.usoltsev.carfactory.details.Body;
import ru.nsu.ccfit.usoltsev.carfactory.storages.BodyStorage;

public class BodyProducer extends Producer<BodyStorage, Body> {
    public BodyProducer(BodyStorage bodyStorage) {
        super(bodyStorage, 1.0, Body.class);
    }

}
