package model;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Esta clase representa un chat, que es un conjunto de mensajes entre dos usuarios
 * contiene el usuario al que se envía el mensaje y una lista de mensajes
 */
@Setter
@Getter
public class Chat implements Serializable {
    private Usuario usuarioReceptor;
    private Usuario usuarioEmisor;
    private ArrayList<Mensaje> listaMensajes;

    public static Integer idOrden = 0;

    //---------------------------------------CONSTRUCTOR PARA EL CHAT---------------------------------------
    public Chat() {
        this.listaMensajes = new ArrayList<>();
    }

    public Chat(Usuario usuarioReceptor, Usuario usuarioEmisor) {
        this.usuarioReceptor = usuarioReceptor;
        this.usuarioEmisor = usuarioEmisor;
        this.listaMensajes = new ArrayList<>();
    }

    public Chat(Usuario usuarioReceptor) {
        this.usuarioReceptor = usuarioReceptor;
        listaMensajes = new ArrayList<>();
    }
    //---------------------------------------METODOS---------------------------------------

    /**
     * Metodo que devuelve el último mensaje del chat
     *
     * @return el último mensaje del chat
     */
    public String getUltimoMensaje() {
        if (listaMensajes.size() > 0) return listaMensajes.get(listaMensajes.size() - 1).getMensaje();
        else return "";
    }

    /**
     * Metodo que permite agregar un mensaje al chat
     *
     * @param mensaje mensaje que se desea agregar
     */
    public void addMensaje(Mensaje mensaje) {
        listaMensajes.add(mensaje);
    }

    public ArrayList<Mensaje> getListaMensajes() {
        return listaMensajes;
    }

    /**
     * Metodo que ordera los mensajes del chat por fecha
     */
    public ArrayList<Mensaje> ordenarMensajes() {
        listaMensajes.sort(Mensaje::compareTo);
        return listaMensajes;
    }
}
