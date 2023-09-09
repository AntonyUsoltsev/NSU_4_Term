package ru.nsu.ccfit.usoltsev.carfactory.storages;

import ru.nsu.ccfit.usoltsev.carfactory.FillListener;
import ru.nsu.ccfit.usoltsev.carfactory.details.Accessories;

public class AccessoryStorage extends Storage<Accessories>{
    public AccessoryStorage(int storageCapacity, FillListener listener) {
        super(storageCapacity, listener);
    }
}
