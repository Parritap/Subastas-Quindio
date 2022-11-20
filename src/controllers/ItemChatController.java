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
import java.io.IOException;
import java.util.Objects;

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

        if(chat.getUsuarioReceptor() == application.getClienteActivo()){
            lblNameUsuario.setText(chat.getUsuarioEmisor().getName());
            if(chat.getUsuarioEmisor().getRutaFotoPerfil() != null) {
                String ruta = chat.getUsuarioReceptor().getRutaFotoPerfil();
                String rutaFinal = obtenerRutPerfilRelativa(ruta);
                String[] rutaSplit = rutaFinal.split("/");
                String rutaFinal2 = rutaSplit[rutaSplit.length - 1];
                try {
                    Image image = new Image(Objects.requireNonNull(getClass().getResource(("/resources/FotosPerfil/" + rutaFinal2))).openStream());
                    circleImage.setFill(new ImagePattern(image));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }else{
            lblNameUsuario.setText(chat.getUsuarioReceptor().getName());
            lblUltimoMensaje.setText(chat.getUltimoMensaje());
            String ruta = chat.getUsuarioReceptor().getRutaFotoPerfil();
            String rutaFinal = obtenerRutPerfilRelativa(ruta);
            String[] rutaSplit = rutaFinal.split("/");
            String rutaFinal2 = rutaSplit[rutaSplit.length - 1];
            try {
                Image image = new Image(Objects.requireNonNull(getClass().getResource(("/resources/FotosPerfil/" + rutaFinal2))).openStream());
                circleImage.setFill(new ImagePattern(image));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public String obtenerRutPerfilRelativa (String ruta){
        String[] arreglo = ruta.split("\\\\");
        System.out.println(arreglo[arreglo.length-1]);
        return "/src/resources/FotosPerfil/"+arreglo[arreglo.length-1];
    }

    /**
     * Metodo que se ejecuta al pulsar sobre el item del chat
     * @param event evento
     */
    @FXML
    void cargarChat(MouseEvent event) {
        chatController.cargarChat(chat);
    }


}
