package persistencia;

import model.EmpresaSubasta;
import model.ModelFactoryController;

import java.io.*;
import java.util.ArrayList;

/**
 * ESTA CLASE SIRVE COMO AYUDA PARA LA LECTURA Y ESCRITURA DE LOS ESTADOS
 * DE LA EMPRESA
 */
public class ArchivoUtil {

    /**
     * Este metodo recibe una cadena con el contenido que se quiere guardar en el archivo
     * @param ruta es la ruta o path donde esta ubicado el archivo
     * @throws IOException
     */
    public static void guardarArchivo(String ruta,String contenido, Boolean flagAnexarContenido) throws IOException {
        if(!existeCarpeta(ruta)){
            crearRuta(ruta);
        }
        FileWriter fw = new FileWriter(ruta,flagAnexarContenido);
        BufferedWriter bfw = new BufferedWriter(fw);
        bfw.write(contenido);
        bfw.close();
        fw.close();
    }

    /**Crea una ruta de carpetas, se utiliza cuando el usuario
     * no tiene el sistema de archivos predefinido
     * @param ruta ruta a crear
     * */
    public static void crearRuta(String ruta) throws IOException {
        String[] dirs = ruta.split("\\\\");
        File folder;
        String rutaActual = "C:\\";
        for(int i=1; i<dirs.length-1; i++){
            rutaActual+=dirs[i]+"\\";
            folder = new File(rutaActual);
            folder.mkdirs();
        }
    }

    /**determina si una carpeta de una ruta existe
     * @param ruta la ruta a determinar si existe
     * @return booleano indicando si la carpeta existe o no
     * */
    public static Boolean existeCarpeta(String ruta){
        File dir = new File(ruta);
        return dir.exists();
    }

    /**
     * ESte metodo retorna el contendio del archivo ubicado en una ruta,con la lista de cadenas.
     * @param ruta
     * @return
     * @throws IOException
     */
    public static ArrayList<String> leerArchivo(String ruta) throws IOException {

        ArrayList<String>  contenido = new ArrayList<String>();
        FileReader fr=new FileReader(ruta);
        BufferedReader bfr=new BufferedReader(fr);
        String linea="";
        while((linea = bfr.readLine())!=null)
        {
            contenido.add(linea);
        }
        bfr.close();
        fr.close();
        return contenido;
    }


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
