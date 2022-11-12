package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MensajeRecibidoController {

        @FXML
        private Label lblMensaje;

        public void setMensaje(String mensaje){
            lblMensaje.setText(mensaje);
        }

}
