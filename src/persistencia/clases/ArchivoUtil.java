package persistencia;

import model.EmpresaSubasta;
import model.ModelFactoryController;
import utilities.Utils;

import java.beans.*;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * ESTA CLASE SIRVE COMO AYUDA PARA LA LECTURA Y ESCRITURA DE LOS ESTADOS
 * DE LA EMPRESA
 */
public class ArchivoUtil {

    //VARIABLE CON LA FECHA EN LA QUE SE CREA EL LOG
    static String fechaSistema = "";


    /**guarda un registro log en la ruta especificada
     *
     * */

    public static void guardarRegistroLog(String mensajeLog, int nivel, String accion, String rutaArchivo) {
        //SI NO EXISTE LA RUTA, LA CREA
        try {
            ArchivoUtil.crearRuta(rutaArchivo);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        String log = "";
        Logger LOGGER = Logger.getLogger(accion);
        FileHandler fileHandler = null;
        cargarFechaSistema();
        try {

            fileHandler = new FileHandler(rutaArchivo,true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            switch (nivel) {
                case 1:
                    LOGGER.log(Level.INFO,accion+","+mensajeLog+","+fechaSistema) ;
                    break;

                case 2:
                    LOGGER.log(Level.WARNING,accion+","+mensajeLog+","+fechaSistema) ;
                    break;

                case 3:
                    LOGGER.log(Level.SEVERE,accion+","+mensajeLog+","+fechaSistema) ;
                    break;

                default:
                    break;
            }

        } catch (SecurityException e) {

            LOGGER.log(Level.SEVERE,e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            LOGGER.log(Level.SEVERE,e.getMessage());
            e.printStackTrace();
        }
        finally {

            fileHandler.close();
        }

    }

    private static void cargarFechaSistema() {

        String diaN = "";
        String mesN = "";
        String anioN = "";

        Calendar cal1 = Calendar.getInstance();


        int  dia = cal1.get(Calendar.DATE);
        int mes = cal1.get(Calendar.MONTH)+1;
        int anio = cal1.get(Calendar.YEAR);
        int hora = cal1.get(Calendar.HOUR);
        int minuto = cal1.get(Calendar.MINUTE);


        if(dia < 10){
            diaN+="0"+dia;
        }
        else{
            diaN+=""+dia;
        }
        if(mes < 10){
            mesN+="0"+mes;
        }
        else{
            mesN+=""+mes;
        }

        //		fecha_Actual+= a�o+"-"+mesN+"-"+ diaN;
        //		fechaSistema = a�o+"-"+mesN+"-"+diaN+"-"+hora+"-"+minuto;
        fechaSistema = anio+"-"+mesN+"-"+diaN;
        //		horaFechaSistema = hora+"-"+minuto;
    }

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


    /**Verifica que una ruta exista, si no es asi, la crea
     * @param ruta ruta que buscamos
     * @throws IOException
     * */
    public static void verificarRuta(String ruta) throws IOException {
        if(!existeCarpeta(ruta)){
            crearRuta(ruta);
        }
    }

    /**Crea una ruta de carpetas, se utiliza cuando el usuario
     * no tiene el sistema de archivos predefinido
     * @param ruta ruta a crear
     * @throws IOException
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

    /**pasa una lista con los nombres de los archivos y carpetas en un directorio
     * @param ruta ruta que queremos listar
     * @return arreglo con los nombres de los archivos y carpetas en ese directorio
     * */
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

    /**deja un archivo en blanco, utili para evitar duplicados en la serializacion
     * @param ruta ruta del archivo a limpiar
     * */
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

    /**copia los contenidos del archivo en rutaOrigen  en el archivo rutaDestino,
     * utilizado a la hora de hacer respaldos de los archivos de serializacion
     * @param rutaOrigen ruta del archivo que queremos copiar
     * @param rutaDestino ruta donde queremos copiar el archivo, si este archivo no existe, es creado
     * @throws IOException
     * */
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

    public static Object cargarRecursoSerializado(String rutaArchivo)throws Exception
    {
        Object aux = null;
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


    /**guarda la informacion de un objeto en un archivo binario
     * @param rutaArchivo ruta donde guardar el archivo
     * @param object objeto a serializar
     * @throws Exception
     * */
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

    /**deserializa un objeto a partir de un xml
     * @param rutaArchivo ruta del archivo que queremos deserializar
     * @throws IOException
     * */


    public static Object cargarRecursoSerializadoXML(String rutaArchivo) throws IOException {

        XMLDecoder decodificadorXML;
        Object objetoXML;

        decodificadorXML = new XMLDecoder(new FileInputStream(rutaArchivo));
        objetoXML = decodificadorXML.readObject();
        decodificadorXML.close();
        return objetoXML;

    }


    /**serializa un objeto en formato xml
     * @param rutaArchivo ruta donde queremos guardar el serializado
     * @param objeto objeto a serializar
     * @throws IOException
     * */
    public static void salvarRecursoSerializadoXML(String rutaArchivo, Object objeto) throws IOException {

        XMLEncoder codificadorXML;
        codificadorXML = new XMLEncoder(new FileOutputStream(rutaArchivo));

        codificadorXML.setPersistenceDelegate(LocalDate.class,
                new PersistenceDelegate() {
                    @Override
                    protected Expression instantiate(Object localDate, Encoder encdr) {
                        return new Expression(localDate,
                                LocalDate.class,
                                "parse",
                                new Object[]{localDate.toString()});
                    }
                });

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
