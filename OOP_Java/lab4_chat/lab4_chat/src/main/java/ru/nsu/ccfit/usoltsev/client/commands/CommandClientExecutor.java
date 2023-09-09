package ru.nsu.ccfit.usoltsev.client.commands;

import ru.nsu.ccfit.usoltsev.client.controller.Controller;
import ru.nsu.ccfit.usoltsev.myUtils.Message;

public interface CommandClientExecutor {
    void execute(Controller controller, Message message);
}
