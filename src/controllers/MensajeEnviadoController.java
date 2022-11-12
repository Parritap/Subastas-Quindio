package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MensajeEnviadoController {

    @FXML
    private Label lblMensaje;


    public void setMensaje(String mensaje){
        lblMensaje.setText(mensaje);
    }


}
