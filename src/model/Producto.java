package model;

import lombok.Data;

import java.io.Serializable;
@Data
public abstract class Producto implements Serializable {

    private Integer id;
    private static int idAux;
    private String descripcion;
    private String nombre;

    public Producto(String nombre, String descripcion){
        id = ++ idAux;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }



}
