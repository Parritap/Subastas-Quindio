package model;

import exceptions.CRUDExceptions;
import exceptions.EscrituraException;
import exceptions.LecturaException;
import interfaces.CRUD;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter
public class IProducto implements CRUD<Producto>, Serializable {

    private ArrayList<Producto> listaProductos = new ArrayList<>();

    /**
     * devuelve la lista con todos los productos
     * @return un arraylist con todos los productos
     * */
    @Override
    public ArrayList<Producto> listar() throws LecturaException {
        if(listaProductos.size() == 0){
            throw new LecturaException("No hay Productos para listar", "intentando acceder a una lista de productos vacía");
        }
        return listaProductos;
    }

    @Override
    public Producto buscarId(Integer id) throws LecturaException {
        for (Producto producto : listaProductos) {
            if (producto.compararId(id)) {
                return producto;
            }
        }

        throw new LecturaException("No se encontró el producto con ese ID", "no se ha encontrado el producto con id "+id);
    }


    private boolean noExisteProducto(Producto producto) throws EscrituraException {
        for (Producto aux: listaProductos){
            if(aux.equals(producto)) throw new EscrituraException("Ya existe un producto con esas caracteristicas", "intentando crer un producto ya existente"+producto.getNombre()+", "+producto.getId());
        }
        return true;
    }

    //crea un Producto y lo agrega a la lista
    @Override
    public void crear(Producto producto) throws CRUDExceptions {
        if(noExisteProducto(producto)) {
            producto.setEstado(Estado.NUEVO);
            listaProductos.add(producto);
        }
    }

    //actualiza un producto con cierto ID a un nuevoProducto
    @Override
    public void actualizar(Integer id, Producto nuevoProducto) throws CRUDExceptions {
        for(int i=0; i<listaProductos.size(); i++){
            if(Objects.equals(listaProductos.get(i).getId(), id)){
                listaProductos.set(i,nuevoProducto);
            }
        }
    }

    @Override
    public void Eliminar(Integer id) throws CRUDExceptions {
        boolean flag = false;
        for (Producto producto : listaProductos) {
            if (producto.compararId(id)) {
                producto.setEstado(Estado.ELIMINADO);
                flag = true;
                break;
            }
        }
        if(!flag) throw new EscrituraException("No se ha podido eliminar el producto", "no se ha podido eliminar el producto con id "+id);
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
        if (!noExisteProducto(producto)) {
            listaProductos.add(producto);
        }
    }

    /**lista los productos de manera ordenada comparators por un atributo
     * @param criterio lambda que se utilizara para saber cuando un elemento es mayor a otro
     * @param orden orden en el que se listaran los elementos, si ascendente o descendente.
     * */
    public ArrayList<Producto> listar(Comparar criterio, TipoOrden orden) throws CRUDExceptions {
        ArrayList<Producto> productos = this.listar();
        ArrayList<Producto> productosOrdenados = new ArrayList<>();
        //índice en el que vamos a insertar cada elemento al ordenar
        int ind = 0;
        for (Producto producto: productos) {
            for (int j = 0; j < productosOrdenados.size(); j++) {
                ind = j;
                if (orden == TipoOrden.ASCENDENTE && !criterio.esMayor(producto, productosOrdenados.get(j))) break;
                if (orden == TipoOrden.DESCENDENTE && criterio.esMayor(producto, productosOrdenados.get(j))) break;
            }
            if (ind >= productosOrdenados.size() - 1) productosOrdenados.add(producto);
            productosOrdenados.add(ind, producto);
        }

        return productosOrdenados;
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

    public void actualizarProductos(IProducto iProducto) {
        listaProductos.addAll(iProducto.getListaProductos());
    }
}
