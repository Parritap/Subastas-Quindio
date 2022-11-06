package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;
import model.Chat;

import java.io.ByteArrayInputStream;

@Getter
@Setter
public class ItemChatController implements IApplication, Inicializable {

    private App application;
    @FXML
    private ImageView imgFotoChat;
    @FXML
    private Label lblNameUsuario;
    @FXML
    private Label lblUltimoMensaje;

    private Chat chat;


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
        lblNameUsuario.setText(chat.getUsuarioReceptor().getName());
        lblUltimoMensaje.setText(chat.getUltimoMensaje());
        imgFotoChat.setImage(new Image(new ByteArrayInputStream(chat.getUsuarioReceptor().getFotoPerfil())));
    }
}
