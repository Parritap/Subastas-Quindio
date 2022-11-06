package services;

import javafx.scene.layout.AnchorPane;
import model.Chat;
import services.client.ClientApplication;
import services.server.ServerApplication;

public class Service {

    private static Integer contador = 0;

    public static void cargarChat(Chat chat, AnchorPane paneChat) {

        if(contador == 0){
            ServerApplication.paneChat = paneChat;
            ServerApplication.main(null);
        }else {
            ClientApplication.paneChatCliente = paneChat;
            ClientApplication.main(null);
        }
        contador++;

    }
}
