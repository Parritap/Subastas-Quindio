package persistencia;

import model.EmpresaSubasta;
import model.ModelFactoryController;

import java.io.IOException;

public class ArchivoUtil {

    private static EmpresaOutput empresaOutput;


    public static void guardar(){
        EmpresaSubasta empresaSubasta = ModelFactoryController.getInstance();
        if(empresaOutput != null){
            guardarAux(empresaSubasta);
        }else{
            empresaOutput = new EmpresaOutput();
            guardarAux(empresaSubasta);
        }
    }

    private static  void guardarAux(EmpresaSubasta empresaSubasta){
        try {
            empresaOutput.abrir();
            empresaOutput.escribir(empresaSubasta);
            empresaOutput.cerrar();
        } catch (IOException e) {
           System.err.println(e.getMessage());
        }
    }



}
