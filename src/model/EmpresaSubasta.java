package model;

import exceptions.EscrituraException;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmpresaSubasta implements Runnable, Serializable {

    private IAnuncio iAnuncio;
    private IUsuario iUsuario;
    private IProducto iProducto;
    private ITransaccion iTransaccion;

    public EmpresaSubasta() {
        iAnuncio = new IAnuncio();
        iProducto = new IProducto();
        iUsuario = new IUsuario();
        try {
            iUsuario.crear(new Usuario("a", null, null, null, null, null));
        } catch (EscrituraException e) {
            throw new RuntimeException(e);
        }
        iTransaccion = new ITransaccion();
    }


    public void crearUsuario(Usuario usuario) throws EscrituraException {
        iUsuario.crear(usuario);
    }

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
