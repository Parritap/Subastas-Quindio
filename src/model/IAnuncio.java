package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import exceptions.CRUDExceptions;
import exceptions.EscrituraException;
import exceptions.LecturaException;
import interfaces.CRUD;
import lombok.Getter;
import lombok.Setter;
import model.enums.Estado;
import model.enums.TipoOrden;

@Getter
@Setter
public class IAnuncio implements CRUD<Anuncio>, Serializable {

    //SE CAMBIA EL HASHMAP POR UN ARRAYLIST, DEBIDO A QUE SE INVIRTIO LA DEPENDENCIA DEL ID
    public ArrayList<Anuncio> listaAnuncios = new ArrayList<>();

    /**
     * Metodo que actualiza la lista de anuncios, se actualiza
     * con la lista contenida en el objeto pasado por parametro
     * se filtra para no aniadir los que ya están en la lista
     *
     * @param iAnuncio objeto que contiene la lista de anuncios
     */
    public void actualizarAnuncios(IAnuncio iAnuncio) {
        for (Anuncio anuncio : iAnuncio.getListaAnuncios()) {
            if (!listaAnuncios.contains(anuncio)) {
                listaAnuncios.add(anuncio);
            }
        }
    }

    /**
     * Método que buscar por el identificador del anuncio.
     *
     * @param id identificador a buscar dentro de la lista de anuncios.
     * @return Anuncio con el identificador pasado por parametro.
     * @throws LecturaException De no encontrar ningún anuncio con el identificador especificado.
     */
    @Override
    public Anuncio buscarId(Integer id) throws LecturaException {
        for (Anuncio listaAnuncio : listaAnuncios) {
            if (listaAnuncio.compararId(id)) {
                return listaAnuncio;
            }
        }
        throw new LecturaException("No se encontró el anuncio con ese ID", "anuncio con ID " + id + " no encontrado");
    }

    /**
     * Método que crea un anuncio haciendo antes las respectivas verificaciones de que no exista uno igual.
     *
     * @param anuncio Anuncio a crear.
     * @throws EscrituraException Si ya existe igual al recibido en el parámetro.
     */
    @Override
    public void crear(Anuncio anuncio) throws EscrituraException {
        if (existeAnuncio(anuncio)) {
            anuncio.setEstado(Estado.NUEVO);
            listaAnuncios.add(anuncio);
        }

    }

    /**
     * METODO QUE PERMITE AGREGAR UN ANUNCIO A LA EMPRESA
     *
     * @param anuncio ANUNCIO A AGREGAR
     * @throws CRUDExceptions SI NO PUEDE AGREGAR MAS ANUNCIO LANZA UNA EXCEPCION
     */
    @Override
    public void add(Anuncio anuncio) throws CRUDExceptions {
        crear(anuncio);
    }

    /**
     * Método que verifica si existe un anuncio antes de crearlo
     *
     * @param anuncio Anuncio a comparar.
     * @return True si el anuncio NO EXISTE
     */
    private boolean existeAnuncio(Anuncio anuncio) {
        for (Anuncio aux : listaAnuncios) {
            if (aux.equals(anuncio)) {
                return false;
            }
        }
        return true;
    }

    /**
     * compara los elementos y ns dice si uno es mayor que el otro
     *
     * @param campo    atributo por el cual se van a comparar los elementos
     * @param anuncio1 anuncio a comparar
     * @param anuncio2 anuncio a comparar
     */

    public static int ordenar(String campo, Anuncio anuncio1, Anuncio anuncio2) {
        int resultado = 0;

        if ("valorInicial".equals(campo)) {
            resultado = anuncio1.getValorInicial().compareTo(anuncio2.getValorInicial());
        }
        return resultado;
    }

    /**
     * PERMITE LISTAR CON UN ORDEN
     *
     * @param campo ATRIBUTO POR EL QUE SE DESEA LISTAR
     * @param dir   ASCENDENTE O DESCENDENTE
     * @return UNA LISTA CON LOS OBJETOS ORDENADOS
     */
    @Override
    public ArrayList<Anuncio> listar(String campo, TipoOrden dir) {
        ArrayList<Anuncio> listaOrdenada = listaAnuncios;
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

    /**
     * Devuelve la lista de anuncios almacenados en la empresa
     *
     * @return listaAnuncios
     */
    public ArrayList<Anuncio> getListaAnuncio() {
        //filtro los anuncios para que no me retorne los que tienen el mismo id
        ArrayList<Anuncio> listaFiltrada = new ArrayList<>();
        for (Anuncio anuncio : listaAnuncios) {
            if (!listaFiltrada.contains(anuncio) && anuncio.getEstado() != Estado.ELIMINADO) {
                listaFiltrada.add(anuncio);
            }
        }
        return listaFiltrada;
    }

    /**
     * To string
     */
    @Override
    public String toString() {
        return "Empresa{" +
                " listaAnuncios=" + listaAnuncios.toString() +
                '}';
    }

    /**
     * Se encarga de recorrer la lista de anuncios y devolver un string con la informacion de cada anuncio
     *
     * @return String con la informacion de cada anuncio
     */
    public String getStringAnuncios() {
        StringBuilder anuncios = new StringBuilder();
        for (Anuncio anuncio : listaAnuncios) {
            anuncios.append(anuncio.getStringAnuncio()).append("\n");
        }
        return anuncios.toString();
    }

    /**
     * Metodo que devuelve la lista anuncios de un usuario específico
     *
     * @param clienteActivo es el usuario que solicita su listaAnuncios
     * @return una lista de anuncios
     */
    public ArrayList<Anuncio> getListaAnuncio(Usuario clienteActivo) {
        ArrayList<Anuncio> listaAnunciosCliente = new ArrayList<>();
        for (Anuncio anuncio : listaAnuncios) {
            if (anuncio.getUsuario().equals(clienteActivo)) {
                listaAnunciosCliente.add(anuncio);
            }
        }
        return listaAnunciosCliente;
    }

    /**
     * Actualizo el estado del anuncioClicker
     *
     * @param anuncioClicked anuncio que se va a actualizar
     */
    public void eliminar(Anuncio anuncioClicked) {
        //busco el anuncio en la lista y lo cambio su estado ha eliminado
        anuncioClicked.setEstado(Estado.ELIMINADO);
    }

    /**
     * Metodo que permite actualizar un anuncio dado otro que contiene
     * la informacion a actualizar
     *
     * @param anuncio  contiene los atributos a actualizar
     * @param producto es el producto que contiene el anuncio
     */
    public void actualizar(Anuncio anuncio, Producto producto) {
        //busco el anuncio en la lista
        for (Anuncio anuncio1 : listaAnuncios) {
            if (anuncio1.compararId(anuncio.getId())) {
                anuncio1.setProducto(producto);
                //actualizo todos los datos del anuncio1 con los datos del anuncio pasado por parametro
                anuncio1.setValorMinimo(anuncio.getValorMinimo());
                anuncio1.setImageSrc(anuncio.getImageSrc());
                anuncio1.setTitulo(anuncio.getTitulo());
                anuncio1.setEstado(Estado.ACTUALIZADO);
                break;
            }
        }
    }

    public void desactivarAnuncios() {

        for (Anuncio a : listaAnuncios) {
            if (a.getFechaPublicacion().isBefore(a.getFechaTerminacion())) {
                a.setEstado(Estado.DESACTIVADO);
            }
        }
    }

    /**
     * Método que permite añadir un puja al anuncio indicado por parámetro
     *
     * @param usuario
     * @param anuncio
     * @param valorPuja
     */
    public void hacerPuja(Usuario usuario, Anuncio anuncio, Double valorPuja) {

        Puja puja = new Puja(LocalDate.now(), usuario, valorPuja);
        anuncio.getListaPujas().add(puja);
        anuncio.setValorActual(valorPuja);
        usuario.getListaPujas().add(puja);
    }

    public ArrayList<Anuncio> filtrarAnuncioPorAsuario(Usuario u) {
        //En caso de que no haya ningún usuario activo, esto es, que no haya iniciado sesión, se devolverá la lista de anuncios completa.
        if (u == null) return this.listaAnuncios;

        ArrayList<Anuncio> lista = new ArrayList<>();
        for (Anuncio a : listaAnuncios) {
            if (!a.getUsuario().equals(u)) lista.add(a);
        }
        return lista;
    }

    /**
     * Metodo que permite crear un chat entre dos usuarios
     * @param clienteActivo usuario que realiza la puja
     * @param vendedor dueño del anuncio
     */
    public void crearChat(Usuario clienteActivo, Usuario vendedor) {
        //creo un chat entre el cliente y el vendedor, devuelve la misma lista
        //para que se guarde en la lista de chats de los usuarios, ambos puedan guardar sus mensajes
        ArrayList<String> listaMensajes = clienteActivo.crearChat(vendedor);
        //creo el chat del vendedor
        vendedor.crearChat(clienteActivo);
        //actualizo la lista de mensajes para que ambos puedan acceder a la misma instancia
        vendedor.setListaMensajes(listaMensajes, clienteActivo);
    }
}

