package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mensaje {

    private String mensaje;
    private String usuarioEmisor;
    private String fecha;
    private String hora;

    public Mensaje(String mensaje, String usuario, String fecha, String hora) {
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
