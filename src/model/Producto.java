package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
@Getter
@Setter

public  class Producto implements Serializable {

    //VARIABLES GLOBALES
    private Integer id;
    private static int idAux;
    private String descripcion;
    private String nombre;
    private Estado estado;

    /**
     * CONSTRUCTOR
     * @param nombre NOMBRE DEL PRODUCTO
     * @param descripcion DESCRIPCION DEL PRODUCTO
     */
    public Producto(String nombre, String descripcion){
        id = ++ idAux;
        this.nombre = nombre;
        this.descripcion = descripcion;
        estado = Estado.NUEVO;
    }

    public Producto(){}

    public boolean compararId(Integer id){
        return this.id.compareTo(id) == 0;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", estado=" + estado +
                '}';
    }
}
