package ru.nsu.ccfit.usoltsev.carfactory.storages;

import ru.nsu.ccfit.usoltsev.carfactory.FillListener;
import ru.nsu.ccfit.usoltsev.carfactory.details.Body;

public class BodyStorage extends Storage<Body>{

    public BodyStorage(int storageCapacity, FillListener listener){
        super(storageCapacity,listener);
    }
}
