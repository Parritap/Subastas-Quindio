package persistencia;

import model.EmpresaSubasta;
import model.ModelFactoryController;

import java.io.IOException;

/**
 * ESTA CLASE SIRVE COMO AYUDA PARA LA LECTURA Y ESCRITURA DE LOS ESTADOS
 * DE LA EMPRESA
 */
public class ArchivoUtil {
    //VARIABLES GLOBALES
    private static EmpresaOutput empresaOutput;
    private static EmpresaInput empresaInput;

    /**
     * METODO QUE PERMITE GUARDAR EL ESTADO INSTANTANEO DE LA EMPRESA
     */
    public static void guardar(){
        EmpresaSubasta empresaSubasta = ModelFactoryController.getInstance();
        if(empresaOutput != null){
            guardarAux(empresaSubasta);
        }else{
            empresaOutput = new EmpresaOutput();
            guardarAux(empresaSubasta);
        }
    }

    /**
     * ESTE METODO GUARDA EL ESTADO DE LA EMPRESA PERO ANTES VERIFICA QUE
     * LA INSTANCIA DE EMPRESA OUTPUT EXISTA
     * @param empresaSubasta RECIBE LA INSTANCIA A GUARDAR
     */
    private static  void guardarAux(EmpresaSubasta empresaSubasta){
        try {
            empresaOutput.abrir();
            empresaOutput.escribir(empresaSubasta);
            empresaOutput.cerrar();
        } catch (IOException e) {
           System.err.println(e.getMessage());
        }
    }

    /**
     * METODO QUE PERMITE LEER UN ESTADO DE EMPRESA
     * @return EL ESTADO DE LA EMPRESA
     */
    private static EmpresaSubasta leer(){
        if (empresaInput == null) {
            empresaInput = new EmpresaInput();
        }
        return empresaInput.leer();
    }



}
