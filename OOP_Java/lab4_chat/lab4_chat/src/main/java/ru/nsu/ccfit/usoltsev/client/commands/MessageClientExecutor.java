package ru.nsu.ccfit.usoltsev.client.commands;

import ru.nsu.ccfit.usoltsev.client.controller.Controller;
import ru.nsu.ccfit.usoltsev.myUtils.CommandAnnotation;
import ru.nsu.ccfit.usoltsev.myUtils.Message;

@CommandAnnotation
public class MessageClientExecutor implements CommandClientExecutor {

    @Override
    public void execute(Controller controller, Message message) {
        controller.getView().printMessages(message);
    }
}
