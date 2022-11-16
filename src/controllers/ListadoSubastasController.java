package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import model.Anuncio;
import model.ModelFactoryController;
import model.Puja;
import model.enums.Estado;
import utilities.Utils;

import java.util.ArrayList;

@Getter
@Setter
public class ListadoSubastasController implements IApplication, Inicializable {

    private Anuncio anuncioClicked;

    private App application;

    @FXML
    private VBox VBoxMisSubastas;

    @FXML
    private VBox VBoxMisPujas;


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
        VBoxMisSubastas.getChildren().clear();
        ArrayList<Anuncio> listadoAnuncio;
        ArrayList<Puja> listadoPujas;
        try {
            listadoAnuncio = ModelFactoryController.getlistaAnuncios(application.getClienteActivo());
            if (listadoAnuncio != null) {
                //filtro los anuncios que esten duplicados en listadoAnuncio
                for (Anuncio anuncio : listadoAnuncio) {
                    if (!(anuncio.getEstado() == Estado.ELIMINADO)) {
                        AnchorPane pane = application.obtenerPaneAnuncio(Utils.SUBASTA_ITEM, anuncio, this);
                        //Añado el pane al VBox
                        VBoxMisSubastas.getChildren().add(pane);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        VBoxMisPujas.getChildren().clear();
        //Lógica para listar las pujas del usuario activo.VBoxMisPujas.getChildren().clear();ArrayList<Puja> listadoPujas;
        try {
            listadoPujas = ModelFactoryController.getListaPujas(application.getClienteActivo());
            if (listadoPujas != null) {
                for (Puja puja : listadoPujas) {
                    Estado e = puja.getAnuncio().getEstado();
                    if (e != Estado.DESACTIVADO && e != Estado.ELIMINADO && puja.getEstado()== Estado.ACTIVO) {
                        AnchorPane pane = application.obtenerPanePuja(Utils.PUJA_ITEM, puja, this);
                        //Añado el pane al VBox
                        VBoxMisPujas.getChildren().add(pane);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void eliminarAnuncio() {
        ModelFactoryController.eliminarAnuncio(anuncioClicked);
        application.abrirAlerta("Anuncio eliminado");
    }

    /**
     * Metodo llamado desde el menu contextual para eliminar un anuncio
     */
    public void actualizarVBox() {
        inicializarComponentes();
    }

    public void actualizarAnuncio() {
        application.abrirActualizarAnuncio(anuncioClicked);
        actualizarVBox();
    }
}
