package ru.nsu.ccfit.usoltsev.carfactory;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.ccfit.usoltsev.carfactory.dealers.Dealer;
import ru.nsu.ccfit.usoltsev.carfactory.producers.AccessoryProducer;
import ru.nsu.ccfit.usoltsev.carfactory.producers.BodyProducer;
import ru.nsu.ccfit.usoltsev.carfactory.producers.EngineProducer;
import ru.nsu.ccfit.usoltsev.carfactory.storages.AccessoryStorage;
import ru.nsu.ccfit.usoltsev.carfactory.storages.AutoStorage;
import ru.nsu.ccfit.usoltsev.carfactory.storages.BodyStorage;
import ru.nsu.ccfit.usoltsev.carfactory.storages.EngineStorage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FactoryProperties.readInfo();
            View view = new View();

            EngineStorage engineStorage = new EngineStorage(FactoryProperties.ENGINE_STORAGE_SIZE, view);
            BodyStorage bodyStorage = new BodyStorage(FactoryProperties.BODY_STORAGE_SIZE, view);
            AccessoryStorage accessoryStorage = new AccessoryStorage(FactoryProperties.ACCESSORY_STORAGE_SIZE, view);

            AutoStorage autoStorage = new AutoStorage(FactoryProperties.AUTO_STORAGE_SIZE, view);

            EngineProducer engineProducer = new EngineProducer(engineStorage);
            BodyProducer bodyProducer = new BodyProducer(bodyStorage);
            AccessoryProducer accessoryProducer = new AccessoryProducer(accessoryStorage);

            Factory factory = new Factory(engineStorage, bodyStorage, accessoryStorage, autoStorage, view);
            AutoStorageController autoStorageController = new AutoStorageController(factory, autoStorage);
            Dealer dealer = new Dealer(autoStorage);

            view.drawMenu(engineProducer, bodyProducer, accessoryProducer, dealer,autoStorageController);

            dealer.start();
            engineProducer.start();
            bodyProducer.start();
            accessoryProducer.start();
            autoStorageController.start();


            Scene scene = new Scene(view.getRoot());
            stage.setScene(scene);
            stage.show();

            stage.setOnHidden(event -> {
                engineProducer.interrupt();
                bodyProducer.interrupt();
                accessoryProducer.interrupt();
                factory.getWorkers().killThreadPool();
                autoStorageController.interrupt();
                dealer.interrupt();
            });

        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }

}