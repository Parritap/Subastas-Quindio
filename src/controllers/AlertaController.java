package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AlertaController implements IApplication, Inicializable {

    private App application;

    @FXML
    private Label lblMensaje;

    /**
     * Metodo que se acciona al hacer clic en aceptar
     * @param ignoredEvent generado al hacer
     */
    @FXML
    void aceptarAction(ActionEvent ignoredEvent) {
        application.cerrarAlerta();
    }
    /**
     * Metodo que se acciona al hacer clic en aceptar
     * @param ignoredEvent generado al hacer
     */
    @FXML
    void cancelarAction(ActionEvent ignoredEvent) {
        application.cerrarAlerta();
    }

    /**
     * Metodo que permite cambiar el mensaje mostrado en el frame
     * @param mensaje mensaje que se quiere mostrar
     */
    public void setMensaje(String mensaje) {
        lblMensaje.setText(mensaje);
    }

    //implementados por la interfaz
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

    }
}
