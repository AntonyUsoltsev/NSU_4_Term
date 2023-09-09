package ru.nsu.ccfit.usoltsev.carfactory.producers;

import ru.nsu.ccfit.usoltsev.carfactory.details.Accessories;
import ru.nsu.ccfit.usoltsev.carfactory.storages.AccessoryStorage;

public class AccessoryProducer extends Producer<AccessoryStorage, Accessories> {
    public AccessoryProducer(AccessoryStorage accessoryStorage) {
        super(accessoryStorage, 1.0, Accessories.class);
    }
}
