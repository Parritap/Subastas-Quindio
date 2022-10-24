package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Anuncio;
import model.Usuario;
import utilities.Utils;

import java.io.IOException;
import java.util.ArrayList;

public class controllerListadoSubastas implements IApplication, Inicializable {

    private App application;

    @FXML
    private VBox VBoxMisSubastas;

    @Override
    public App getApplication() {
        return application;
    }

    @Override
    public void setApplication(App application) {
        this.application = application;
    }

    @Override
    public void inicializarComponentes() {
        ArrayList<Anuncio> listadoAnuncio = new ArrayList<>();
        try {
            listadoAnuncio = application.getClienteActivo().getListaAnuncios();
        } catch (Exception e) {
            //Do nothing.
        }
        for (Anuncio anuncio : listadoAnuncio) {
            for (int i = 0; i <100 ; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Utils.SUBASTA_ITEM));
                try {
                    AnchorPane anchorPane = fxmlLoader.load();
                    subastaItemController itemController = fxmlLoader.getController();
                    itemController.setAnuncio(anuncio);
                    itemController.setApplication(application);
                    itemController.inicializarComponentes();
                    VBoxMisSubastas.getChildren().add(anchorPane);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
