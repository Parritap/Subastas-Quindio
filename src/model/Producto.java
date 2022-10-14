package model;

import lombok.Data;

import java.io.Serializable;
@Data
public class Producto implements Serializable {

    //VARIABLES GLOBALES
    private Integer id;
    private static int idAux;
    private String descripcion;
    private String nombre;

    /**
     * CONSTRUCTOR
     * @param nombre NOMBRE DEL PRODUCTO
     * @param descripcion DESCRIPCION DEL PRODUCTO
     */
    public Producto(String nombre, String descripcion){
        id = ++ idAux;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }



}
