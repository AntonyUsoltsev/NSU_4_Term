package ru.nsu.ccfit.usoltsev.client.commands;

import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.usoltsev.client.controller.Controller;
import ru.nsu.ccfit.usoltsev.myUtils.CommandAnnotation;
import ru.nsu.ccfit.usoltsev.myUtils.Message;

import java.util.List;

@CommandAnnotation
@Log4j2
public class MembersUpdateClientExecutor implements CommandClientExecutor{

    @Override
    public void execute(Controller controller, Message message) {
        log.info("Update chat members");
        controller.getView().updateMembers((List<String>) message.getContent());
    }
}
