package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Producto {

    //VARIABLES GLOBALES
    private Integer id;
    private static int idAux;
    private String descripcion;
    private String nombre;
    private Estado estado;
    private Integer precioInicial;

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

    public boolean compararId(Integer id) {
        return this.id.compareTo(id) == 0;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }
}
