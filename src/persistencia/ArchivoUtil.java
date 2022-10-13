package persistencia;

import model.EmpresaSubasta;
import model.ModelFactoryController;
import utilities.Utils;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.nio.file.Files;
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
        verificarRuta(ruta);
        FileWriter fw = new FileWriter(ruta,flagAnexarContenido);
        BufferedWriter bfw = new BufferedWriter(fw);
        bfw.write(contenido);
        bfw.close();
        fw.close();
    }


    /**Verific que una ruta exista si no es asi, la crea
     * @param ruta ruta que buscamos
     * */
    public static void verificarRuta(String ruta) throws IOException {
        if(!existeCarpeta(ruta)){
            crearRuta(ruta);
        }
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
        folder = new File(ruta);
        folder.createNewFile();
    }

    public static String[] listarDir(String ruta){
        String[] rutas;
        File f = new File(ruta);
        rutas = f.list();
        return rutas;
    }

    /**determina si una carpeta de una ruta existe
     * @param ruta la ruta a determinar si existe
     * @return booleano indicando si la carpeta existe o no
     * */
    public static Boolean existeCarpeta(String ruta){
        File dir = new File(ruta);
        return dir.exists();
    }

    public static void limpiarArchivo(String ruta){
        try {
            guardarArchivo(ruta, "", false);
        }
        catch(IOException e){
            System.out.println("no se pudo limpiar el archivo");
            e.printStackTrace();
        }
    }

    /**
     * ESte metodo retorna el contendio del archivo ubicado en una ruta,con la lista de cadenas.
     * @param ruta
     * @return
     * @throws IOException
     */
    public static ArrayList<String> leerArchivo(String ruta) throws IOException {
        verificarRuta(ruta);

        ArrayList<String>  contenido = new ArrayList<String>();
        FileReader fr = new FileReader(ruta);
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

    public static void copiarArchivo(String rutaOrigen, String rutaDestino) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            verificarRuta(rutaOrigen);
            verificarRuta(rutaDestino);

            is = new FileInputStream(rutaOrigen);
            os = new FileOutputStream(rutaDestino);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
        catch (IOException e){
            System.out.println("no se pudieron copiar los archivos");
            e.printStackTrace();
        }
        finally {
            is.close();
            os.close();
        }

    }

    //------------------------------------SERIALIZACI�N  y XML
    /**
     * Escribe en el fichero que se le pasa el objeto que se le envia
     *
     * @param rutaArchivo
     *            path del fichero que se quiere escribir
     * @throws IOException
     */

    @SuppressWarnings("unchecked")
    public static Object cargarRecursoSerializado(String rutaArchivo)throws Exception
    {
        Object aux = null;
//		Empresa empresa = null;
        ObjectInputStream ois = null;
        try {
            // Se crea un ObjectInputStream
            ois = new ObjectInputStream(new FileInputStream(rutaArchivo));

            aux = ois.readObject();

        } catch (Exception e2) {
            throw e2;
        } finally {
            if (ois != null)
                ois.close();
        }
        return aux;
    }


    public static void salvarRecursoSerializado(String rutaArchivo, Object object)	throws Exception {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo));
            oos.writeObject(object);
        } catch (Exception e) {
            throw e;
        } finally {
            if (oos != null)
                oos.close();
        }
    }




    public static Object cargarRecursoSerializadoXML(String rutaArchivo) throws IOException {

        XMLDecoder decodificadorXML;
        Object objetoXML;

        decodificadorXML = new XMLDecoder(new FileInputStream(rutaArchivo));
        objetoXML = decodificadorXML.readObject();
        decodificadorXML.close();
        return objetoXML;

    }

    public static void salvarRecursoSerializadoXML(String rutaArchivo, Object objeto) throws IOException {

        XMLEncoder codificadorXML;

        codificadorXML = new XMLEncoder(new FileOutputStream(rutaArchivo));
        codificadorXML.writeObject(objeto);
        codificadorXML.close();

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
