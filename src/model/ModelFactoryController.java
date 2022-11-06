package model;

import exceptions.LecturaException;
import persistencia.logic.Persistencia;
import java.util.ArrayList;
import exceptions.CRUDExceptions;
import exceptions.EscrituraException;
import java.util.Objects;

/**
 * SINGLETON
 */
public class ModelFactoryController {

    //VARIABLE GENERAL PARA TODA LA EMPRESA
    private static EmpresaSubasta empresaSubasta;
    private static Integer idListaPujas=0;
    /**
     * METODO QUE DEVUELVE LA INSTANCIA DE LA EMPRESA
     * @return LA INSTANCIA DE LA EMPRESA
     */
    public static EmpresaSubasta getInstance(){
        try {
            deserializarEmpresa();
        } catch (CRUDExceptions e) {
            throw new RuntimeException(e);
        }
        return Objects.requireNonNullElseGet(empresaSubasta, () -> {
            try {
                return empresaSubasta = new EmpresaSubasta();
            } catch (CRUDExceptions e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void deserializarEmpresa() throws CRUDExceptions {
        EmpresaSubasta empresaSubastaAux = Persistencia.deserializarEmpresaBinario();
        //actualizo las variables inscritas dentro de empresa
        if(empresaSubasta != null && empresaSubastaAux != null) empresaSubasta.actualizarImplementaciones(empresaSubastaAux);

        //hace una copia de seguridad del xml
    }

    /**da un ID para la lista de pujas, usualmente a objetos Usuario o Anuncio
     * este es necesario para deserializar
     * */
    public static Integer darIdListaPuja(){
        idListaPujas++;
        return idListaPujas;
    }

    /**
     * Metodo que devuelve la lista de usuarios contenida en la empresa
     * @return lista de usuarios
     */
    public static ArrayList<Anuncio> getlistaAnuncios() {
        return getInstance().getListaAnuncios();
    }

    /**
     * metodo que permite crear un usuario, el usuario se crea en la instancia de la empresa contenida
     * en el singleton
     * @param usuario el usuario que se va a crear
     * @throws EscrituraException si no se puede crear el usuario
     */
    public static void crearUsuario(Usuario usuario) throws EscrituraException{
        empresaSubasta.crearUsuario(usuario);
    }

    /**
     * Metodo que permite crear anuncios en el singleton
     * @param anuncio que se va a crear
     * @param producto el producto que contiene el anuncio
     * @param clienteActivo el cliente que lo creo
     */
    public static void crearAnuncio(Anuncio anuncio, Producto producto, Usuario clienteActivo) throws CRUDExceptions{
        empresaSubasta.crearAnuncio(anuncio, producto, clienteActivo);
    }

    /**
     * Permite actualizar la informacion de un usuario
     * @param clienteActivo el cliente activo con su informacion actual
     * @param usuario un objeto usuario auxiliar que contiene la informacion a actualizar
     * @throws LecturaException si no se puede actualizar
     */
    public static void actualizarUsuario(Usuario clienteActivo, Usuario usuario) throws LecturaException {
        empresaSubasta.actualizarUsuario(clienteActivo, usuario);
    }

    /**
     * Este metodo devuelve un String con la forma de serializar todos los usuarios
     * @return String con la lista de usuarios serializada
     */
    public static String getStringUsuarios() {
        return empresaSubasta.getStringUsuarios();
    }

    /**
     * Este metodo devuelve un String con la forma de serializar todos los anuncios
     * @return String con la lista de anuncios serializada
     */
    public static String getStringProductos() {
        return empresaSubasta.getStringProductos();
    }

    /**
     * Este metodo devuelve un String con la forma de serializar todos los productos
     * @return String con la lista de productos serializada
     */
    public static String getStringTransacciones() {
        return empresaSubasta.getStringTransacciones();
    }

    /**
     * Este metodo devuelve un String con la forma de serializar todos los anuncios
     * @return String con la lista de anuncios serializada
     */
    public static String getStringAnuncios() {
        return empresaSubasta.getStringAnuncios();
    }

    /**
     * Metodo que devuelve todos los anuncios de un cliente
     * @param clienteActivo el cliente que creo los anuncios
     * @return una lista de anuncios
     */
    public static ArrayList<Anuncio> getlistaAnuncios(Usuario clienteActivo) {
        return empresaSubasta.getListaAnuncios(clienteActivo);
    }

    /**
     * Metodo que permite eliminar un anuncio, dado ese anuncio
     * @param anuncioClicked el anuncio que se va a eliminar
     */
    public static void eliminarAnuncio(Anuncio anuncioClicked) {
        empresaSubasta.eliminarAnuncio(anuncioClicked);
    }

    /**
     * Metodo que permite actualizar un anuncio dado un anuncio con toda la informacion actualizada
     * y el producto que contiene
     * @param anuncio el anuncio con la informacion actualizada
     * @param producto el producto que contiene el anuncio
     */
    public static void actualizarAnuncio(Anuncio anuncio, Producto producto) {
        empresaSubasta.actualizarAnuncio(anuncio, producto);
    }

    /**
     * Metodo que desactiva un anuncio para que no se pueda ver
     */
    public static void desactivarAnuncios() {
        empresaSubasta.getIAnuncio().desactivarAnuncios();
    }

    /**
     * metodo que permite crear una puja, la puja se crea en la instancia de la empresa contenida
     * @param usuario el usuario que hace la puja
     * @param anuncio el anuncio por el que se puja
     * @param valorPuja cuanto puja
     */
    public static void hacerPuja (Usuario usuario, Anuncio anuncio, Double valorPuja) {
        empresaSubasta.hacerPuja(usuario, anuncio, valorPuja);
    }

    /**
     * Metodo que devuelve la lista de pujas de un anuncio
     * @param u el usuario que hizo la puja
     * @return la lista de pujas
     */
    public static ArrayList<Anuncio> obtenerListaAnunciosSegunUsuario(Usuario u) {
        return empresaSubasta.getIAnuncio().filtrarAnuncioPorAsuario(u);
    }

    /**
     * Metodo que permite crear un chat cuando se hace una puja
     * @param clienteActivo cliente que realiza la puja
     * @param vendedor vendedor del anuncio
     */
    public static void crearChat(Usuario clienteActivo, Usuario vendedor) {
        empresaSubasta.crearChat(clienteActivo, vendedor);
    }
}
