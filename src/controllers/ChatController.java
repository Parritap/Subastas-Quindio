package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import model.Chat;
import model.Mensaje;
import model.Usuario;
import services.AppCliente;
import utilities.Utils;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

@Setter
@Getter
public class ChatController implements IApplication, Inicializable {

    private App application;

    @FXML
    private TextField txtMensaje;
    @FXML
    private VBox VBoxMensajes;
    @FXML
    private VBox vboxListaChats;
    @FXML
    private ImageView imgProfile;
    @FXML
    private Label lblNombre;
    private AppCliente appCliente;
    private Usuario usuarioEnElChat;

    private Chat chatActual;


    @Override
    public void inicializarComponentes() {
        iniciarCliente();
        vboxListaChats.getChildren().clear();
        Usuario usuario = application.getClienteActivo();
        ArrayList<Chat> listaChats = usuario.getListaChats();
        if (listaChats != null) {
            //filtro los chats que esten duplicados en listaChats
            for (Chat chat : listaChats) {
                for (int i = 0; i < 100; i++) {
                    AnchorPane pane = application.obtenerChatItem(chat, this);
                    //Añado el pane al VBox
                    vboxListaChats.getChildren().add(pane);
                }

            }
        }

    }

    private void iniciarCliente() {
        new Thread(() -> {
            appCliente = new AppCliente("localhost",8081);
            appCliente.iniciarCliente();
        }).start();
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
     *
     * @param event generado por el botón
     */
    @FXML
    void cargarInicio(ActionEvent event) {
        application.loadScene(Utils.frameInicio);
    }

    @FXML
    void enviarMensaje(ActionEvent event) {

        if(usuarioEnElChat != null){
            Mensaje mensaje = new Mensaje();
            mensaje.setMensaje(txtMensaje.getText());
            mensaje.setUsuarioEmisor(application.getClienteActivo());
            mensaje.setUsuarioReceptor(usuarioEnElChat);
            appCliente.enviarMensaje(mensaje);
            txtMensaje.setText("");
            cargarChat(chatActual);
        }

    }

    public void cargarChat(Chat chat) {
        chatActual = chat;
        imgProfile.setImage(new Image(new ByteArrayInputStream(chat.getUsuarioReceptor().getFotoPerfil())));
        lblNombre.setText(chat.getUsuarioReceptor().getName());
        usuarioEnElChat = chat.getUsuarioReceptor();
        VBoxMensajes.getChildren().clear();
        ArrayList<Mensaje> listaMensajes = chat.getListaMensajes();
        if (listaMensajes != null) {
            for (Mensaje mensaje : listaMensajes) {
                AnchorPane pane = application.obtenerMensajeItem(mensaje);
                VBoxMensajes.getChildren().add(pane);
            }
        }
    }
}
