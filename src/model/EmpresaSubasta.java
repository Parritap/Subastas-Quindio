package model;

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
            iProducto = new IProducto();
        }

}
