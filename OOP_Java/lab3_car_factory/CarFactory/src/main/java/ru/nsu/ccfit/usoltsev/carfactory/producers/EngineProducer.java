package ru.nsu.ccfit.usoltsev.carfactory.producers;

import ru.nsu.ccfit.usoltsev.carfactory.details.Engine;
import ru.nsu.ccfit.usoltsev.carfactory.storages.EngineStorage;


public class EngineProducer extends Producer<EngineStorage, Engine> {

    public EngineProducer(EngineStorage storage) {
        super(storage, 1.0, Engine.class);
    }

}
