package controllers;

import application.App;
import interfaces.IApplication;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    private ImageView image;
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
            image.setImage(img);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
