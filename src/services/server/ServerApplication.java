package services.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Objects;
@Getter
@Setter
public class ServerApplication extends Application {

    public static AnchorPane paneChat;

    @Override
    public void start(Stage primaryStage) throws IOException {
        paneChat = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("server-view.fxml")));
    }

    public static void main(String[] args) {
        launch();
    }
}