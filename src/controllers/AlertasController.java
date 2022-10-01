package controllers;

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.IApplication;

public class AlertasController implements IApplication {

    private App application;
    @FXML
    private Label lblMensaje;

    @FXML
    void aceptar(ActionEvent event) {
        application.cerrarAlerta();
    }

    public void setLabel(String text) {
        lblMensaje.setText(text);
    }

    @Override
    public App getApplication() {
        return null;
    }

    @Override
    public void setApplication(App application){
        this.application = application;
    }


}
