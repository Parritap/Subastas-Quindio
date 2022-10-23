package persistencia.logic;

import lombok.Data;
import model.EmpresaSubasta;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * ESTA CLASE SE ENCARGA DE GESTIONAR LA LECTURA DE ARCHIVOS
 * GUARDADOS EN EL DISCO
 */
@Data
public class EmpresaInput {
    //VARIABLES GLOBALES PARA LA LECTURA DE ARCHIVOS
    private FileInputStream file;
    private ObjectInputStream input;

    /**
     * METODO QUE ABRE UNA CONNEXION CON EL DISCO
     * PARA PERMITIR LA LECTURA
     * @throws IOException SI NO ENCUENTRA EL ARCHIVO LANZA UNA EXCEPCION
     */
    public void abrir() throws IOException {
        file=new FileInputStream("empresa.ser");
        input=new ObjectInputStream(file);
    }

    /**
     * METODO QUE PERMITE CERRAR LA CONNEXION DE LECTURA CON EL DISCO
     * @throws IOException SI NO PUEDE CERRAR LA CONNEXION LANZA UNA EXCEPCION
     */
    public void cerrar() throws IOException {
        if(input != null) input.close();
    }

    /**
     * METODO QUE DEVUELVE EL ESTADO DE LA EMPRESA EN LA ULTIMA VEZ QUE
     * SE EJECUTO
     * @return EMPRESA EN SU ULTIMO ESTADO
     */
    public EmpresaSubasta leer(){
        EmpresaSubasta empresa = null;
        if(input != null){
            try {
                empresa = (EmpresaSubasta) input.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return empresa;
    }
}
