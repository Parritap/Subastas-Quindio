package controllers;

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import interfaces.IApplication;
import lombok.Data;

/**
 * ESTA CLASE PERMITE CONTROLAS LAS ACCIONES
 * DE LAS ALERTAS
 */
@Data
public class AlertasController implements IApplication {

    //VARIABLES GLOBALES
    private App application;
    @FXML
    private Label lblMensaje;

    /**
     * METODO QUE ACEPTA LA ALERTA CON EL MENSAJE
     * @param ignoredEvent EVENTO GENERADO AL HACER CLIC
     */
    @FXML
    void aceptar(ActionEvent ignoredEvent) {
        application.cerrarAlerta();
    }

    /**
     * METODO QUE PERMITE CAMBIAR EL TEXTO DEL LABEL
     * @param text TEXTO QUE SE DESEA MOSTRAR
     */
    public void setLabel(String text) {
        lblMensaje.setText(text);
    }
    //METODOS IMPLEMENTADOS POR LA INTERFAZ
    @Override
    public App getApplication() {
        return null;
    }
    @Override
    public void setApplication(App application){
        this.application = application;
    }


}
