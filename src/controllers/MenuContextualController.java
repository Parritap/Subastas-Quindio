package controllers;

import application.App;
import interfaces.IApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuContextualController implements IApplication {

    private ListadoSubastasController listadoSubastasController;
    private App application;
    @FXML
    void actualizarAnuncio(ActionEvent event) {
        listadoSubastasController.actualizarAnuncio();
        listadoSubastasController.actualizarVBox();
    }

    @FXML
    void eliminarAnuncio(ActionEvent event) {
        listadoSubastasController.eliminarAnuncio();
        listadoSubastasController.actualizarVBox();
    }

    @FXML
    void verInfoAnuncio(ActionEvent event) {

    }

    @Override
    public App getApplication() {
        return application;
    }

    @Override
    public void setApplication(App application) {
        this.application = application;
    }
}
