package services.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ClientApplication extends Application {

    public static AnchorPane paneChatCliente;
    @Override
    public void start(Stage primaryStage) throws IOException {
        paneChatCliente = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("client-view.fxml")));
    }

    public static void main(String[] args) {
        launch();
    }
}
