package MyExceptions;

import java.io.BufferedWriter;
import java.io.IOException;

public class FabricExceptions extends MyExceptions {
    public FabricExceptions(final String expMessage) {
        super(expMessage);
    }

    public void printException(final BufferedWriter errorStream)throws IOException {
        errorStream.write(expMessage + '\n');
        errorStream.flush();
        LOGGER.severe(expMessage + "\n");
    }
}
