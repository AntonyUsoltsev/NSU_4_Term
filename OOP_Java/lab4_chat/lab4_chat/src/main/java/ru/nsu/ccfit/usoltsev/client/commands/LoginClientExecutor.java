package ru.nsu.ccfit.usoltsev.client.commands;

import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.usoltsev.client.controller.Controller;
import ru.nsu.ccfit.usoltsev.myUtils.Message;
import ru.nsu.ccfit.usoltsev.myUtils.CommandAnnotation;
import java.io.IOException;

@Log4j2
@CommandAnnotation
public class LoginClientExecutor implements CommandClientExecutor {

    @Override
    public void execute(Controller controller, Message message) {
        if (Boolean.TRUE.equals(Boolean.parseBoolean((String) message.getContent()))) {
            try {
                log.info("Log in: successfully");
                log.info("Change view to Chat");
                controller.getView().swapToChat();
                controller.getUser().run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            controller.getView().printErrorMessage((String) message.getContent());
        }
    }
}
