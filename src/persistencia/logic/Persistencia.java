package persistencia.logic;


import model.*;
import utilities.Utils;

public class Persistencia {



    /**
     * Metodo que permite serializar un objeto en un archivo
     */
    public static EmpresaSubasta deserializarEmpresaBinario(){
        return (EmpresaSubasta) ArchivoUtil.deserializarBinario(Utils.RUTA_EMPRESA_SER);
    }


    public static void serializarEmpresaBinario() {
        ArchivoUtil.serializarBinario(Utils.RUTA_EMPRESA_SER, ModelFactoryController.getInstance());
    }
}
