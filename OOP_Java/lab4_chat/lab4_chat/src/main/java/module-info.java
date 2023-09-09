//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

module ru.nsu.ccfit.usoltsev {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires log4j;
    requires org.jetbrains.annotations;

    requires org.apache.logging.log4j;
    requires java.xml;

    exports ru.nsu.ccfit.usoltsev.client;
    exports ru.nsu.ccfit.usoltsev.server;

    opens ru.nsu.ccfit.usoltsev.client to javafx.fxml;

    exports ru.nsu.ccfit.usoltsev.client.view;
    opens ru.nsu.ccfit.usoltsev.client.view to javafx.fxml;
    exports ru.nsu.ccfit.usoltsev.client.controller;
    opens ru.nsu.ccfit.usoltsev.client.controller to javafx.fxml;
    exports ru.nsu.ccfit.usoltsev.client.model;
    opens ru.nsu.ccfit.usoltsev.client.model to javafx.fxml;
    exports ru.nsu.ccfit.usoltsev.myUtils;
}
