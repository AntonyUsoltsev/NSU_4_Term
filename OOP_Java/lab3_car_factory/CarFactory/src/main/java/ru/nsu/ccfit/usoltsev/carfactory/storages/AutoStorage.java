package ru.nsu.ccfit.usoltsev.carfactory.storages;

import ru.nsu.ccfit.usoltsev.carfactory.FillListener;
import ru.nsu.ccfit.usoltsev.carfactory.details.Auto;

public class AutoStorage extends Storage<Auto> {
    public AutoStorage(int storageCapacity, FillListener listener){
        super(storageCapacity,listener);
    }

}
