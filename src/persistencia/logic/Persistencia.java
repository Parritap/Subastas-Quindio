package persistencia.logic;

import java.io.IOException;

import exceptions.CRUDExceptions;
import model.*;
import utilities.Utils;

public class Persistencia {


    /**
     * Este metodo permite serializar la empresa en binario.
     * Se guarda en el archivo "empresa. Ser" ubicado en la carpeta "archivos"
     * @return una instancia de la empresa
     * @throws CRUDExceptions 
     */
    public static EmpresaSubasta deserializarEmpresaBinario() throws CRUDExceptions{
    	//si el archivo de serializacion esta vacio devuelve un objeto vacio
    	if(ArchivoUtil.archivoVacio(Utils.RUTA_EMPRESA_SER)) { return new EmpresaSubasta();}
        return (EmpresaSubasta) ArchivoUtil.deserializarBinario(Utils.RUTA_EMPRESA_SER);
    }

    /**
     * Metodo que permite serializar la empresa en un archivo binario
     * se pasa la ruta donde se va a guardar el archivo, esta se halla en archivo util
     */
    public static void serializarEmpresaBinario() {
        ArchivoUtil.serializarBinario(Utils.RUTA_EMPRESA_SER, ModelFactoryController.getInstance());
    }

    /**
     * Este metodo permite serializar la empresa en un txt, cada implementación retorna un string
     * con los datos contenidos, se pasa la ruta donde se va a guardar el archivo, esta se halla en archivo util
     */
    public static void serializarEmpresaTXT() {
        //obtengo el string de los usuarios
        String usuariosTxt = ModelFactoryController.getStringUsuarios();
        ArchivoUtil.serializarTxt(Utils.RUTA_USUARIOS_TXT, usuariosTxt);
        //obtengo el string de los productos
        String productosTxt = ModelFactoryController.getStringProductos();
        ArchivoUtil.serializarTxt(Utils.RUTA_PRODUCTOS_TXT, productosTxt);
        //obtengo el string de las transacciones
        String transaccionesTxt = ModelFactoryController.getStringTransacciones();
        ArchivoUtil.serializarTxt(Utils.RUTA_TRANSACCIONES_TXT, transaccionesTxt);
        //obtengo el string de los anuncios
        String anunciosTxt = ModelFactoryController.getStringAnuncios();
        ArchivoUtil.serializarTxt(Utils.RUTA_ANUNCIOS_TXT, anunciosTxt);
        //obtengo el string de las pujas
        String pujasTxt = ModelFactoryController.getStringPujas();
        ArchivoUtil.serializarTxt(Utils.RUTA_PUJAS_TXT, pujasTxt);
        
    }
    
    
    public static void serializarEmpresaXML() throws IOException {
    	ArchivoUtil.salvarRecursoSerializadoXML(Utils.RUTA_EMPRESA_XML, ModelFactoryController.getInstance());
    }
    
    public static EmpresaSubasta deserializarEmpresaXML() throws IOException, CRUDExceptions {
    	if(!ArchivoUtil.archivoVacio(Utils.RUTA_EMPRESA_XML))
    		return (EmpresaSubasta) ArchivoUtil.cargarRecursoSerializadoXML(Utils.RUTA_EMPRESA_XML);
    	return new EmpresaSubasta();
    }
}
