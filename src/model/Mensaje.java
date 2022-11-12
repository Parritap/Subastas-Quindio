package model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Mensaje implements Serializable {

    private String mensaje;
    private Usuario usuarioEmisor;

    private Usuario usuarioReceptor;
    private String fecha;
    private String hora;

    public Mensaje() {}

    public Mensaje(String mensaje, Usuario usuario, String fecha, String hora) {
        this.mensaje = mensaje;
        this.usuarioEmisor = usuario;
        this.fecha = fecha;
        this.hora = hora;
    }


    @Override
    public String toString() {
        return "Mensaje{" +
                "mensaje='" + mensaje + '\'' +
                ", usuario='" + usuarioEmisor + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                '}';
    }
}
