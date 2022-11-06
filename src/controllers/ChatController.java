package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Anuncio;
import model.Chat;
import model.Usuario;
import model.enums.Estado;
import utilities.Utils;

import java.util.ArrayList;

public class ChatController implements IApplication, Inicializable {

    private App application;
    @FXML
    private AnchorPane paneChat;

    @FXML
    private VBox vboxListaChats;

    @Override
    public void inicializarComponentes() {
        vboxListaChats.getChildren().clear();
        Usuario usuario = application.getClienteActivo();
        ArrayList<Chat> listaChats = usuario.getListaChats();
        if (listaChats != null) {
            //filtro los chats que esten duplicados en listaChats
            for (Chat chat : listaChats) {
                AnchorPane pane = application.obtenerChatItem(chat);
                //Añado el pane al VBox
                vboxListaChats.getChildren().add(pane);
            }
        }

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
}
