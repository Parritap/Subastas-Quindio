package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Data;
import model.EmpresaSubasta;
import model.ModelFactoryController;

@Data

public class App extends Application {

    private static EmpresaSubasta empresaSubasta;

    @Override
    public void start(Stage stage) throws Exception {
        empresaSubasta = ModelFactoryController.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/FrameInicial.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }



}
