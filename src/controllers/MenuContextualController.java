package controllers;

import application.App;
import interfaces.IApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MenuContextualController implements IApplication {

    private App application;
    @FXML
    void actualizarAnuncio(ActionEvent event) {

    }

    @FXML
    void eliminarAnuncio(ActionEvent event) {
        application.eliminarAnuncio();
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
