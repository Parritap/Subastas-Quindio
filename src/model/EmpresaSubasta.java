package model;

import exceptions.EscrituraException;
import lombok.Data;

@Data
public class EmpresaSubasta {

        private IAnuncio iAnuncio;
        private IUsuario iUsuario;
        private IProducto iProducto;
        private ITransaccion iTransaccion;

        public EmpresaSubasta(){
            iAnuncio = new IAnuncio();
            iProducto = new IProducto();
            iUsuario = new IUsuario();
            iTransaccion = new ITransaccion();
        }


    public void crearUsuario(Usuario usuario) throws EscrituraException {
            iUsuario.crear(usuario);
    }

    public boolean existeUsuario(Usuario usuario) {
            return iUsuario.existeUsuario(usuario);
    }
}
