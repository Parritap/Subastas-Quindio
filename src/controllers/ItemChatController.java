package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;
import model.Chat;

import java.io.ByteArrayInputStream;

@Getter
@Setter
public class ItemChatController implements IApplication, Inicializable {

    private App application;
    @FXML
    private Label lblNameUsuario;
    @FXML
    private Circle circleImage;
    @FXML
    private Label lblUltimoMensaje;
    private Chat chat;

    private ChatController chatController;



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
        Image img = new Image(new ByteArrayInputStream(chat.getUsuarioReceptor().getFotoPerfil()));
        circleImage.setFill(new ImagePattern(img));
    }

    @FXML
    void cargarChat(MouseEvent event) {

        //chatController.cargarChat(chat);
    }


}
