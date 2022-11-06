package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import utilities.Utils;

public class ChatController implements IApplication, Inicializable {

    private App application;
    @FXML
    private AnchorPane paneChat;

    @Override
    public void inicializarComponentes() {

    }

    @Override
    public App getApplication() {
        return application;
    }

    @Override
    public void setApplication(App application) {
        this.application = application;
    }

    /**
     * Evento que se ejecuta al pulsar el botón de volver al menú principal
     * @param event generado por el botón
     */
    @FXML
    void cargarInicio(ActionEvent event) {
        application.loadScene(Utils.frameInicio);
    }
}
