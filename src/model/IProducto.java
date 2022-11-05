package model;

import exceptions.CRUDExceptions;
import exceptions.LecturaException;
import interfaces.CRUD;
import lombok.Getter;
import lombok.Setter;
import model.enums.Estado;
import model.enums.TipoOrden;
import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
public class IProducto implements CRUD<Producto>, Serializable {

    private ArrayList<Producto> listaProductos = new ArrayList<>();

    @Override
    public Producto buscarId(Integer id) throws LecturaException {
        for (Producto producto : listaProductos) {
            if (producto.compararId(id)) {
                return producto;
            }
        }

        throw new LecturaException("No se encontró el producto con ese ID", "no se ha encontrado el producto con id "+id);
    }

    /**
     * Metodo que verifica si un producto existe en la lista
     * @param producto La acción de verificar si existe un producto
     */
    public boolean existeProducto(Producto producto) {
        for (Producto producto1 : listaProductos) {
            if (producto1.compararId(producto.getId())) {
                return false;
            }
        }
        return true;
    }

    //crea un Producto y lo agrega a la lista
    @Override
    public void crear(Producto producto) throws CRUDExceptions {
        if(existeProducto(producto)) {
            producto.setEstado(Estado.NUEVO);
            listaProductos.add(producto);
        }
    }

    /**compara los elementos y ns dice si uno es mayor que el otro*/
    public static int ordenar(String campo, Producto prod1, Producto prod2) {
        int resultado = 0;

        if ("nombre".equals(campo)) {
            resultado = prod1.getNombre().compareTo(prod2.getNombre());
        }
        return resultado;
    }


    /**lista los elementos de manera ordenada comparators mediante un atributo "campo"
     * @param campo atributo por el cual se comparan los elementos
     * @param dir forma en la que se ordenan, ascendente o descendente*/
    @Override
    public ArrayList<Producto> listar (String campo, TipoOrden dir){
        ArrayList<Producto> listaOrdenada = listaProductos;
        listaOrdenada.sort((a, b) -> {
            int resultado = 0;
            if (dir == TipoOrden.ASCENDENTE) {
                resultado = ordenar(campo, a, b);
            } else if (dir == TipoOrden.DESCENDENTE) {
                resultado = ordenar(campo, b, a);
            }
            return resultado;
        });
        return listaOrdenada;
    }


    /**agrega un producto a la lista*/
    @Override
    public void add(Producto producto) throws CRUDExceptions {
        if (existeProducto(producto)) {
            listaProductos.add(producto);
        }
    }

    /**
     * To string
     */
    @Override
    public String toString() {
        return "ProductoDAO{" +
                "listaProductos=" + listaProductos.toString() +
                '}';
    }

    /**
     * Metodo que actualiza la lista productos, que
     * se contienen en el Iproducto pasados por parametros
     * @param iProducto objeto que contiene la lista de los productos
     */
    public void actualizarProductos(IProducto iProducto) {
        for (Producto producto : iProducto.getListaProductos()) {
            if (!listaProductos.contains(producto)) {
                listaProductos.add(producto);
            }
        }
    }

    /**
     * Metodo que devuelve la lista de productos en un String
     * @return Devuelve un string con la informacion de los productos
     */
    public String getStringProductos() {
        StringBuilder stringProductos = new StringBuilder();
        for (Producto listaProductos : listaProductos) {
            stringProductos.append(listaProductos.getStringProducto());
            stringProductos.append("\n");
        }
        return stringProductos.toString();
    }

    public void actualizar(Producto producto) {
        for (Producto producto1 : listaProductos) {
            if (producto1.compararId(producto.getId())) {
                producto1.setNombre(producto.getNombre());
                producto1.setEstado(Estado.ACTUALIZADO);
                producto1.setTipoProducto(producto.getTipoProducto());
            }
        }
    }
}
