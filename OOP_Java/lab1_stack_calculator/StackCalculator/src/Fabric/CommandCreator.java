package Fabric;

import java.io.*;
import java.util.*;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import Commands.Command;
import MyExceptions.CreateCommandsExceptions;
import Logging.MyLogger;
import MyExceptions.FabricExceptions;

public class CommandCreator {
    private final Map<String, Class<? extends Commands.Command>> commandClassesMap;
    private static final String CONFIG_FILE_NAME = "FabricConfig";
    private final Logger LOGGER = MyLogger.getLogger();

    public CommandCreator() throws FabricExceptions {
        commandClassesMap = new HashMap<>();
        loadCommands();
    }

    private void loadCommands() throws FabricExceptions {
        try (InputStream inputStream = CommandCreator.class.getResourceAsStream(CONFIG_FILE_NAME);
             BufferedReader configFile = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = configFile.readLine()) != null) {
                String[] fabricConfigParts = line.split(" ");
                if (fabricConfigParts.length != 2) {
                    throw new FabricExceptions("Invalid fabric config line: " + line + ".");
                }

                Class<? extends Command> curClass = loadCommandClass(fabricConfigParts[1]);
                commandClassesMap.put(fabricConfigParts[0], curClass);
            }
            LOGGER.info("Command creator has created commands.\n");

        } catch (IOException ioExc) {
            throw new FabricExceptions(ioExc.getMessage());
        } catch (NullPointerException nullPtrExc) {
            throw new FabricExceptions("Fabric configuration file not found");
        }
    }

    private Class<? extends Command> loadCommandClass(String className) throws FabricExceptions {
        try {
            Class<?> curClass = Class.forName(className);
            if (!Command.class.isAssignableFrom(curClass)) {
                throw new FabricExceptions(className + " does not implement Command abstract class.");
            }
            return curClass.asSubclass(Command.class);
        } catch (ClassNotFoundException e) {
            throw new FabricExceptions(className + " hasn't been found.");
        }
    }

    public Commands.Command createCommands(String commandName, BufferedWriter errorStream) throws IOException {
        try {
            Class<? extends Command> commandClass = commandClassesMap.get(commandName);
            if (commandClass == null) {
                throw new CreateCommandsExceptions("Unknown command:\"" + commandName + "\".");
            }

            try {
                return commandClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new CreateCommandsExceptions("Failed to create command: " + commandName + "' while creating command.");
            }

        } catch (CreateCommandsExceptions crCommExc) {
            crCommExc.printException(errorStream);
            return null;
        }
    }
}
