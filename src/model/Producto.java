package model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public  class Producto implements Serializable {

    //VARIABLES GLOBALES
    private Integer id;
    private Integer precioInicial;
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
    }

    public Producto(){

    }

    public boolean compararId(Integer id){
        return this.id.compareTo(id) == 0;
    }

}
