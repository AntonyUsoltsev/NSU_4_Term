package ru.nsu.ccfit.usoltsev.server.command.commands;

import ru.nsu.ccfit.usoltsev.server.Server;
import ru.nsu.ccfit.usoltsev.server.command.CommandServerExecutor;
import ru.nsu.ccfit.usoltsev.myUtils.CommandAnnotation;
import ru.nsu.ccfit.usoltsev.myUtils.Message;

@CommandAnnotation
public class LogOutServerExecutor implements CommandServerExecutor {

    @Override
    public void execute(Server server, Message message) {
        server.logOut();
    }
}
