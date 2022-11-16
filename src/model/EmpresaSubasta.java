package model;

import exceptions.CRUDExceptions;
import exceptions.EscrituraException;
import exceptions.LecturaException;
import lombok.Getter;
import lombok.Setter;
import model.enums.Estado;
import utilities.Utils;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Getter
@Setter
public class EmpresaSubasta implements Runnable, Serializable {

    //Para no romper el programa en cada computadora que deserializes el proyecto, lo que haremos será
    //declarar una constante con el numero se serialización específico para esta clase.

    @Serial
    private static final long serialVersionUID = -619891098261150697L;

    //VARIABLES GLOBALES
    private IAnuncio iAnuncio;
    private IUsuario iUsuario;
    private IProducto iProducto;
    private ITransaccion iTransaccion;
    private IPuja iPuja;

    /**
     * CONSTRUCTOR
     */
    public EmpresaSubasta() throws CRUDExceptions {
        iAnuncio = new IAnuncio();
        iProducto = new IProducto();
        iUsuario = new IUsuario();
        iTransaccion = new ITransaccion();
        iPuja = new IPuja();
    }

    /**
     * METODO QUE PERMITE CREAR UN USUARIO USANDO LOS METODOS
     * DE LA INSTANCIA IUSUARIO
     * @param usuario EL QUE SE DESEA AGREGAR
     * @throws EscrituraException SI EL USUARIO YA EXISTE SE LANZA UNA EXCEPCION
     */
    public void crearUsuario(Usuario usuario) throws EscrituraException {
        iUsuario.crear(usuario);
    }


    /**
     * Este metodo define las acciones para cuando se inicie el hilo
     */
    @Override
    public void run() {}

    public ArrayList<Anuncio> getListaAnuncios() {
        return iAnuncio.getListaAnuncio();
    }

    public void crearAnuncio(Anuncio anuncio, Producto producto, Usuario clienteActivo) throws CRUDExceptions {
        anuncio.setProducto(producto);
        iProducto.add(producto);
        anuncio.setUsuario(clienteActivo);
        iAnuncio.add(anuncio);
    }

    public void actualizarUsuario(Usuario clienteActivo, Usuario usuario) throws LecturaException {
        iUsuario.actualizar(clienteActivo.getId(), usuario);
    }

    /**
     * To string
     */

    @Override
    public String toString() {
        return "EmpresaSubasta{" +
                "iAnuncio=" + iAnuncio.toString() +
                ", iUsuario=" + iUsuario.toString() +
                ", iProducto=" + iProducto.toString() +
                ", iTransaccion=" + iTransaccion.toString() +
                '}';
    }

    /**
     * Este metodo permite actualizar las implementaciones cargadas de la
     * persistencia
     * @param empresaSubastaAux es la version de la empresa guardada en la persistencia
     */
    public void actualizarImplementaciones(EmpresaSubasta empresaSubastaAux) {

        iUsuario.actualizarUsuarios(empresaSubastaAux.getIUsuario());
        iAnuncio.actualizarAnuncios(empresaSubastaAux.getIAnuncio());
        iProducto.actualizarProductos(empresaSubastaAux.getIProducto());
        iTransaccion.actualizarTransaccion(empresaSubastaAux.getITransaccion());
    }

    /**
     * Metodo que devuelve la informacion de los usuarios contenidos en un string
     * @return string con la informacion de los usuarios
     */
    public String getStringUsuarios() {
        return iUsuario.getStringUsuarios();
    }
    
    
    /**
     * Metodo que devuelve la informacion de los usuarios contenidos en un string
     * @return string con la informacion de los usuarios
     */
    public String getStringPujas() {
        return iPuja.getStringPujas();
    }
    
    
    
    /**
     * Metodo que devuelve la informacion de los productos contenidos en un string
     * @return string con la informacion de los productos
     */
    public String getStringProductos() {
        return iProducto.getStringProductos();
    }

    /**
     * Metodo que devuelve la informacion de los anuncios contenidos en un string
     * @return string con la informacion de los anuncios
     */
    public String getStringTransacciones() {
        return iTransaccion.getStringTransacciones();
    }

    /**
     * Metodo que devuelve la informacion de los anuncios contenidos en un string
     * @return string con la informacion de los anuncios
     */
    public String getStringAnuncios() {
        return iAnuncio.getStringAnuncios();
    }

    /**
     * Metodo que dado un usuario devuelve su lista de anuncios
     * @param clienteActivo el cliente que solicita la lista de anuncios
     * @return lista de anuncios del usuario
     */
    public ArrayList<Anuncio> getListaAnuncios(Usuario clienteActivo) {
        return iAnuncio.getListaAnuncio(clienteActivo);
    }

    public void eliminarAnuncio(Anuncio anuncioClicked) {
        iAnuncio.eliminar(anuncioClicked);
    }

    public void actualizarAnuncio(Anuncio anuncio, Producto producto) {
        iProducto.actualizar(producto);
        iAnuncio.actualizar(anuncio, producto);
    }

    public void hacerPuja(Usuario usuario, Anuncio anuncio, Double valorPuja) throws EscrituraException {
        iAnuncio.hacerPuja(usuario, anuncio, valorPuja, iPuja);
    }

    public void eliminarPuja(Puja puja) {
        puja.setEstado(Estado.ELIMINADO);
    }
}
