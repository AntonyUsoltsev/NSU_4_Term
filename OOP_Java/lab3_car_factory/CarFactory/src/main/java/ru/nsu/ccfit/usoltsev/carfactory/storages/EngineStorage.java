package ru.nsu.ccfit.usoltsev.carfactory.storages;

import ru.nsu.ccfit.usoltsev.carfactory.FillListener;
import ru.nsu.ccfit.usoltsev.carfactory.details.Engine;


public class EngineStorage extends Storage<Engine> {
    public EngineStorage(int storageCapacity, FillListener listener){
        super(storageCapacity,listener);
    }
}
