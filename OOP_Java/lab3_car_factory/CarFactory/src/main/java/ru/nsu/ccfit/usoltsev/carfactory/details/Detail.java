package ru.nsu.ccfit.usoltsev.carfactory.details;

import java.util.UUID;

public class Detail {
    protected final UUID ID;

    public Detail() {
        this.ID = UUID.randomUUID();
    }

    public String getID() {
        return String.valueOf(ID);
    }
}
