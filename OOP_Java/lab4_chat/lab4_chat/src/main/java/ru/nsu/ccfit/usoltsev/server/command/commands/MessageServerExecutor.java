package ru.nsu.ccfit.usoltsev.server.command.commands;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.usoltsev.server.Server;
import ru.nsu.ccfit.usoltsev.server.command.CommandServerExecutor;
import ru.nsu.ccfit.usoltsev.myUtils.CommandAnnotation;
import ru.nsu.ccfit.usoltsev.myUtils.Message;

///TODO: params in annotation
@CommandAnnotation
public class MessageServerExecutor implements CommandServerExecutor {

    @Override
    public void execute(@NotNull Server server, @NotNull Message message) {
        server.getKernelServer().addMessage(server.getUserName(), String.valueOf(message.getContent()));
    }
}
