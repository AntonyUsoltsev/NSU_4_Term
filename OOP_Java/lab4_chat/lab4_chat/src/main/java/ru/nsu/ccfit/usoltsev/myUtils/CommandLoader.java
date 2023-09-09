package ru.nsu.ccfit.usoltsev.myUtils;

import javafx.fxml.LoadException;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Properties;

@Log4j2
public class CommandLoader {
    private final Properties properties;

    public CommandLoader(String linkCommandsProperties) throws IOException {
        properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(linkCommandsProperties)) {
            properties.load(inputStream);
        } catch (IOException io) {
            log.warn("Constructor loader ClientCommands.properties " + io.getMessage());
            throw new LoadException("classCommands.properties was not found" + io.getMessage());
        }
    }

    @SneakyThrows
    public <T> T createInstanceClass(@NotNull String nameCommand) {
        log.info("Find class with name '" + nameCommand + "' by nameCommand");

        Class<?> clazz;
        T command;
        try {
            clazz = Class.forName(properties.getProperty(nameCommand));
        } catch (NullPointerException | ClassNotFoundException ex) {
            throw new NullPointerException("This command wasn't found " + ex.getMessage());
        }

        Annotation[] annotations = clazz.getAnnotations();

        log.info("Annotation comparison CommandAnnotation");
        for (Annotation annotation : annotations) {
            if (annotation instanceof CommandAnnotation) {
                try {
                    log.info("Try to get instance of " + clazz);
                    command = (T) clazz.getDeclaredConstructor().newInstance();
                    log.info(clazz + " instance successfully created");
                } catch (InstantiationException | IllegalAccessException ex) {
                    log.error("Error: occurred at the time of command creation " + nameCommand);
                    throw new LoadException("Command: " + nameCommand + Arrays.toString(ex.getStackTrace()));
                }
                return command;
            }
        }
        log.trace("Class wasn't found");
        log.warn("Command not found or class wasn't annotated");
        return null;
    }
}