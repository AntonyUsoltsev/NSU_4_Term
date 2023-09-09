package ru.nsu.ccfit.usoltsev.server.command;

import ru.nsu.ccfit.usoltsev.myUtils.Message;
import ru.nsu.ccfit.usoltsev.server.Server;

public interface CommandServerExecutor {
    void execute(Server server, Message message);
}
