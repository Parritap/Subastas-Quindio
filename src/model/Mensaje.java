package model;

import lombok.Getter;
import lombok.Setter;
import utilities.Utils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;

@Getter
@Setter
public class Mensaje implements Serializable, Comparable<Mensaje> {

    private String mensaje;
    private Usuario usuarioEmisor;

    private Usuario usuarioReceptor;
    private LocalDateTime fecha;


    public Mensaje() {}

    public Mensaje(String mensaje, Usuario usuario) {
        this.mensaje = mensaje;
        this.usuarioEmisor = usuario;
        this.fecha = LocalDateTime.now();
    }




    @Override
    public String toString() {
        return "Mensaje{" +
                "mensaje='" + mensaje + '\'' +
                ", usuario='" + usuarioEmisor + '\'' +
                ", fecha='" + fecha + '\'' ;
    }

    @Override
    public int compareTo(Mensaje o) {
        if(this.fecha.isBefore(o.fecha)) return -1;
        else if(this.fecha.isAfter(o.fecha)) return 1;
        else return 0;
    }
}
