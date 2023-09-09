module ru.nsu.ccfit.usoltsev.carfactory {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    opens ru.nsu.ccfit.usoltsev.carfactory to javafx.fxml;
    exports ru.nsu.ccfit.usoltsev.carfactory;
    exports ru.nsu.ccfit.usoltsev.carfactory.details;
    exports ru.nsu.ccfit.usoltsev.carfactory.storages;
    exports ru.nsu.ccfit.usoltsev.carfactory.producers;
    exports ru.nsu.ccfit.usoltsev.carfactory.dealers;
    exports ru.nsu.ccfit.usoltsev.carfactory.threadPool;
    opens ru.nsu.ccfit.usoltsev.carfactory.details to javafx.fxml;
}