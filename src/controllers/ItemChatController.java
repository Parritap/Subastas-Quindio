package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ItemChatController implements IApplication, Inicializable {

    private App application;
    @FXML
    private ImageView imgFotoChat;

    @FXML
    private Label lblNameUsuario;

    @FXML
    private Label lblUltimoMensaje;


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
