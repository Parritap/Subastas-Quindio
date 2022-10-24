package model;

import exceptions.CRUDExceptions;
import exceptions.ContraseniaNoValidaException;
import exceptions.EscrituraException;
import exceptions.LecturaException;
import lombok.Getter;
import lombok.Setter;
import persistencia.logic.Persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class EmpresaSubasta implements Runnable, Serializable {

    //VARIABLES GLOBALES
    private IAnuncio iAnuncio;
    private IUsuario iUsuario;
    private IProducto iProducto;
    private ITransaccion iTransaccion;

    /**
     * CONSTRUCTOR
     */
    public EmpresaSubasta() throws CRUDExceptions {
        iAnuncio = new IAnuncio();
        iProducto = new IProducto();
        iUsuario = new IUsuario();
        iTransaccion = new ITransaccion();
    }

    /**
     * METODO QUE PERMITE CREAR UN USUARIO USANDO LOS METODOS
     * DE LA INSTANCIA IUSUARIO
     * @param usuario EL QUE SE DESEA AGREGAR
     * @throws EscrituraException SI EL USUARIO YA EXISTE SE LANZA UNA EXCEPCION
     */
    public void crearUsuario(Usuario usuario) throws EscrituraException {
        iUsuario.crear(usuario);
        Persistencia.registrarAccion("Se ha creado el usuario con email "+usuario.getCorreo(), "Creacion de usuario", ModelFactoryController.getRutaRegistroAcciones());
    }

    /**
     * METODO QUE VERIFICA LA EXISTENCIA DE UN USUARIO PASADO POR
     * PARAMETRO
     * @param usuario QUE SE DESEA VERIFICAR
     * @return TRUE || FALSE
     */
    public boolean existeUsuario(Usuario usuario) {
        return iUsuario.existeUsuario(usuario);
    }

    /**
     * Este metodo define las acciones para cuando se inicie el hilo
     */
    @Override
    public void run() {

    }

    public ArrayList<Anuncio> getListaAnuncios() {
        return iAnuncio.getListaAnuncio();
    }

    public void crearAnuncio(Anuncio anuncio, Producto producto, Usuario clienteActivo) throws CRUDExceptions {
        anuncio.setProducto(producto);
        anuncio.setUsuario(clienteActivo);
        iAnuncio.add(anuncio);
        Persistencia.registrarAccion("Se ha creado un anuncio con el id: " + anuncio.getId(), "Creacion de anuncio", ModelFactoryController.getRutaRegistroAcciones());
    }

    public void actualizarUsuario(Usuario clienteActivo, Usuario usuario) throws LecturaException {
        iUsuario.actualizar(clienteActivo.getId(), usuario);
        Persistencia.registrarAccion("Se actualizó el usuario con correo: " + clienteActivo.getCorreo(), "Actualizacion de usuario", ModelFactoryController.getRutaRegistroAcciones());
    }

    public void addAnuncio(Anuncio anuncio) throws CRUDExceptions {
        iAnuncio.add(anuncio);
        Persistencia.registrarAccion("Se agregó el anuncio con id: " + anuncio.getId(), "Anuncio agregado", ModelFactoryController.getRutaRegistroAcciones());
    }
}
