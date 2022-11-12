package controllers;

import application.App;
import interfaces.IApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Mensaje;

public class MensajeRecibidoController implements IApplication {

    private App application;
    @FXML
    private Label lblMensaje;

    public void setMensaje(String mensaje) {
        lblMensaje.setText(mensaje);
    }

    @Override
    public App getApplication() {
        return application;
    }

    @Override
    public void setApplication(App application) {
        this.application = application;
    }

    public void inicializarComponentes(Mensaje mensaje){
        lblMensaje.setText(mensaje.getMensaje());
    }
}
