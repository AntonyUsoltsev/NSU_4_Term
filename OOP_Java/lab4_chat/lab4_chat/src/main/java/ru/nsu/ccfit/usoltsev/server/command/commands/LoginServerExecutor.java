package ru.nsu.ccfit.usoltsev.server.command.commands;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.usoltsev.server.command.CommandServerExecutor;
import ru.nsu.ccfit.usoltsev.myUtils.CommandAnnotation;
import ru.nsu.ccfit.usoltsev.server.KernelServer;
import ru.nsu.ccfit.usoltsev.myUtils.Message;
import ru.nsu.ccfit.usoltsev.server.Server;
import ru.nsu.ccfit.usoltsev.server.exceptions.NameContainsException;
import ru.nsu.ccfit.usoltsev.server.exceptions.NameFormatException;
import ru.nsu.ccfit.usoltsev.server.exceptions.NameLengthException;

import java.io.ObjectOutputStream;

@Log4j2
@CommandAnnotation
public class LoginServerExecutor implements CommandServerExecutor {
    private KernelServer kernelServer;
    private static final int NAME_MAX_LENGTH = 30;
    private ObjectOutputStream writer;

    @Contract(pure = true)
    private boolean isCorrectNameFormat(final @NotNull String nameUser) throws NameFormatException {
        if (nameUser.matches("^\\w+$"))
            return true;
        throw new NameFormatException("Wrong name format, name contains letters(a-z | A-z), numbers(0-9), and symbol(_)");
    }

    @Contract(pure = true)
    private boolean isLengthCorrect(final @NotNull String name) throws NameLengthException {
        if (name.length() < NAME_MAX_LENGTH)
            return true;
        throw new NameLengthException("Name is too long");
    }

    private boolean containCheck(final @NotNull String name) throws NameContainsException {
        if (!kernelServer.containsName(name))
            return false;
        throw new NameContainsException("Name already contain");
    }

    @SneakyThrows
    private boolean isNameCorrect(final String name) {
        try {
            return isCorrectNameFormat(name) && !containCheck(name) && isLengthCorrect(name) ;
        } catch (NameFormatException | NameContainsException | NameLengthException e) {
            writer.writeObject(new Message<>("Login", e.getMessage()));
            writer.flush();
            log.error("Client type wrong name: " + e.getMessage());
            return false;
        }
    }

    @SneakyThrows
    public void execute(@NotNull Server server, @NotNull Message message) {
        this.writer = server.getWriter();
        this.kernelServer = server.getKernelServer();
        log.info("Try to registration client");
        if (!isNameCorrect(String.valueOf(message.getContent()))) {
            return;
        }
        writer.writeObject(new Message<>("Login", "True"));
        writer.flush();
        log.info("Add client with name: " + message.getContent());
        server.setUserName(String.valueOf(message.getContent()));
        kernelServer.addServer(String.valueOf(message.getContent()), server);
    }
}
