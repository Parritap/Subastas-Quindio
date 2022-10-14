package model;

import exceptions.EscrituraException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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
    public EmpresaSubasta() {
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
}
