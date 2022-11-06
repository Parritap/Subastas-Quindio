package model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Esta clase representa un chat, que es un conjunto de mensajes entre dos usuarios
 * contiene el usuario al que se envía el mensaje y una lista de mensajes
 */
@Setter
@Getter
public class Chat {
    private Usuario usuarioReceptor;
    private ArrayList<String> mensajes;

    public Chat(Usuario usuarioReceptor) {
        this.usuarioReceptor = usuarioReceptor;
        mensajes = new ArrayList<>();
    }
}
