package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import model.Anuncio;
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
        ArrayList<Anuncio> listadoAnuncio = new ArrayList<>();
        try {
            listadoAnuncio = application.getClienteActivo().getListaAnuncios();
        } catch (Exception e) {
            //Do nothing.
        }
        for (int i = 0; i < listadoAnuncio.size(); i++) {
            System.out.println(listadoAnuncio.get(i));
        }
        /*for (Anuncio anuncio : listadoAnuncio) {
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
        }*/
    }
}
