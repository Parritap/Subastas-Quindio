package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lombok.Getter;
import lombok.Setter;
import model.Chat;
import model.Mensaje;
import model.Usuario;
import services.AppCliente;
import utilities.Utils;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Objects;

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
                    AnchorPane pane = application.obtenerChatItem(chat, this);
                    //Añado el pane al VBox
                    vboxListaChats.getChildren().add(pane);
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
            application.enviarMensaje(mensaje);
            txtMensaje.setText("");
            chatActual.addMensaje(mensaje);
            addMessagePropio(mensaje.getMensaje());
        }
    }

    public void addMessagePropio(String messageToSend) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(messageToSend);

        //aumentar el tamaño de la fuente
        text.setStyle("-fx-font-size: 20px;");

        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle(
                "-fx-color: rgb(239, 242, 255);" +
                        "-fx-background-color: rgb(15, 125, 242);" +
                        "-fx-background-radius: 20px;");

        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.color(0.934, 0.925, 0.996));

        hBox.getChildren().add(textFlow);
        VBoxMensajes.getChildren().add(hBox);
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
                if(Objects.equals(mensaje.getUsuarioEmisor().getId(), application.getClienteActivo().getId())) {
                    addMessagePropio(mensaje.getMensaje());
                }else{
                    addMessageAjeno(mensaje.getMensaje());
                }
            }
        }
    }

    private void addMessageAjeno(String mensaje) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(mensaje);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle(
                "-fx-background-color: rgb(233, 233, 235);" +
                        "-fx-background-radius: 20px;");

        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(() -> VBoxMensajes.getChildren().add(hBox));
    }


}
