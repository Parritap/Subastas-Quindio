package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Anuncio;
import model.ModelFactoryController;
import utilities.Utils;

import java.util.ArrayList;

public class ListadoSubastasController implements IApplication, Inicializable {

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
        ArrayList<Anuncio> listadoAnuncio;
        try {
            listadoAnuncio = ModelFactoryController.getlistaAnuncios(application.getClienteActivo());
            if(listadoAnuncio != null){
                //filtro los anuncios que esten duplicados en listadoAnuncio
                for (Anuncio anuncio : listadoAnuncio) {
                    AnchorPane pane = application.obtenerPaneAnuncio(Utils.SUBASTA_ITEM, anuncio);
                    //Añado el pane al VBox
                    VBoxMisSubastas.getChildren().add(pane);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
