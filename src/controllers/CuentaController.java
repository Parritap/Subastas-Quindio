package controllers;

import application.App;
import interfaces.IApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utilities.Utils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *Esta clase se encarga de manejar los eventos y funcionalidades de la vista de cuenta
 */
public class CuentaController implements IApplication {

    //Instancia de application
    private App application;
    //imagen de la cuenta
    @FXML
    private Circle circleImage;
    //Item para controlar la edad
    @FXML
    private Spinner<Integer> edadSpinner;
    //metodo que permite cargar una imagen en la cuenta
    @FXML
    void cargarPerfil(MouseEvent ignoredEvent) {
        //el file chooser permite abrir el explorador
        FileChooser dc = new FileChooser();
        File file = dc.showOpenDialog(new Stage());
        //obtengo el arreglo de bits de la imagen
        byte[] btImagen;
        try {
            //cargo la imagen al pane
            btImagen = Files.readAllBytes(file.toPath());
            Image img = new Image(new ByteArrayInputStream(btImagen), 199, 199, false, false);
            circleImage.setFill(new ImagePattern(img));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void irAlInicio(ActionEvent ignoredEvent) {
        application.loadScene(Utils.frameInicio);
    }

    @FXML
    void paneListadoSubasta(ActionEvent ignoredEvent) {

    }

    @FXML
    void paneMyAccount(ActionEvent ignoredEvent) {

    }
    @FXML
    void initialize() {
        circleImage.setFill(new ImagePattern(new Image("/resources/profile.png")));
    }

    /**
     * Metodos implementados debido a la interfaz
     * @return instancia de Application
     */
    @Override
    public App getApplication() {
        return application;
    }

    /**
     * Setter de application
     * @param application instancia de application
     */
    @Override
    public void setApplication(App application) {
        this.application=application;
    }
}
