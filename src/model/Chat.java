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
    private ArrayList<String> listaMensajes;

    //---------------------------------------CONSTRUCTOR PARA EL CHAT---------------------------------------
    public Chat(Usuario usuarioReceptor) {
        this.usuarioReceptor = usuarioReceptor;
        listaMensajes = new ArrayList<>();
    }
    //---------------------------------------METODOS---------------------------------------
    /**
     * Metodo que devuelve el último mensaje del chat
     * @return el último mensaje del chat
     */
    public String getUltimoMensaje() {
        if(listaMensajes.size() > 0) {
            return listaMensajes.get(listaMensajes.size() - 1);
        }
        return "";
    }
}
