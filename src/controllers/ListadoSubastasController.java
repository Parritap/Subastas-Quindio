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
        try {
            listadoAnuncio = ModelFactoryController.getlistaAnuncios(application.getClienteActivo());
            if(listadoAnuncio != null){
                //filtro los anuncios que esten duplicados en listadoAnuncio
                for (Anuncio anuncio : listadoAnuncio) {
                    if(!(anuncio.getEstado() == Estado.ELIMINADO)){
                        AnchorPane pane = application.obtenerPaneAnuncio(Utils.SUBASTA_ITEM, anuncio, this);
                        //Añado el pane al VBox
                        VBoxMisSubastas.getChildren().add(pane);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarAnuncio(){
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
