package persistencia.logic;

import model.EmpresaSubasta;

import java.io.*;

public class EmpresaOutput {

    private FileOutputStream file;

    private ObjectOutputStream output;

    public void abrir() throws IOException {
        file = new FileOutputStream("empresa.ser");
        output = new ObjectOutputStream(file);
    }


    public void cerrar() throws IOException {
        if(output!= null){
            output.close();
        }
    }

    public void escribir(EmpresaSubasta empresa) throws IOException {

        if(output != null){
            output.writeObject(empresa);
        }
    }

}
