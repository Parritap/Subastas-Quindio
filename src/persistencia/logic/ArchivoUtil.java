package persistencia.logic;

import model.EmpresaSubasta;
import model.ModelFactoryController;
import java.beans.*;
import java.io.*;
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


    /**
     * guarda un registro log en la ruta especificada
     */

    public static void guardarRegistroLog(String mensajeLog, int nivel, String accion, String rutaArchivo) {
        //SI NO EXISTE LA RUTA, LA CREA
        try {
            ArchivoUtil.crearRuta(rutaArchivo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logger LOGGER = Logger.getLogger(accion);
        FileHandler fileHandler = null;
        cargarFechaSistema();
        try {

            fileHandler = new FileHandler(rutaArchivo, true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            switch (nivel) {
                case 1 -> LOGGER.log(Level.INFO, accion + "," + mensajeLog + "," + fechaSistema);
                case 2 -> LOGGER.log(Level.WARNING, accion + "," + mensajeLog + "," + fechaSistema);
                case 3 -> LOGGER.log(Level.SEVERE, accion + "," + mensajeLog + "," + fechaSistema);
                default -> {
                }
            }

        } catch (SecurityException | IOException e) {

            LOGGER.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } finally {
            assert fileHandler != null;
            fileHandler.close();
        }

    }

    public static void guardarRegistroLogExceptions(Exception exception, String mensaje,int nivel) {

        String ruta = null;
        String exceptionStackTrace = null;
        //SI NO EXISTE LA RUTA, LA CREA
        try {
            StringWriter sw = new StringWriter(); //Estas dos líneas lo que hacen es permitir convertir el stacktrace en un String.
            PrintWriter pw = new PrintWriter(sw); //Estas dos líneas lo que hacen es permitir convertir el stacktrace en un String.
            exception.printStackTrace(pw);

            exceptionStackTrace = sw.toString();

            ArchivoUtil.crearRuta(ModelFactoryController.getRutaLogException());
            ruta = ModelFactoryController.getRutaLogException(); //obtenemos el String de la ruta del archivo log.
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logger LOGGER = Logger.getLogger("Registro de Excepcion");
        FileHandler fileHandler = null;
        cargarFechaSistema();
        try {

            assert ruta != null;
            fileHandler = new FileHandler(ruta, true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            switch (nivel) {
                case 1 -> LOGGER.log(Level.INFO, fechaSistema +   " " +mensaje +"\n{\n" + exceptionStackTrace + "}");
                case 2 -> LOGGER.log(Level.WARNING, fechaSistema +" " +mensaje +"\n{\n" + exceptionStackTrace + "}");
                case 3 -> LOGGER.log(Level.SEVERE, fechaSistema + " " +mensaje +"\n{\n" + exceptionStackTrace + "}");
                default -> {
                }
            }

        } catch (SecurityException e) {

            LOGGER.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            LOGGER.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } finally {

            assert fileHandler != null;
            fileHandler.close();
        }

    }

    private static void cargarFechaSistema() {

        String diaN = "";
        String mesN = "";

        Calendar cal1 = Calendar.getInstance();


        int dia = cal1.get(Calendar.DATE);
        int mes = cal1.get(Calendar.MONTH) + 1;
        int anio = cal1.get(Calendar.YEAR);


        if (dia < 10) {
            diaN += "0" + dia;
        } else {
            diaN += "" + dia;
        }
        if (mes < 10) {
            mesN += "0" + mes;
        } else {
            mesN += "" + mes;
        }

        fechaSistema = anio + "-" + mesN + "-" + diaN;

    }

    /**
     * Este metodo recibe una cadena con el contenido que se quiere guardar en el archivo
     *
     * @param ruta es la ruta o path donde está ubicado el archivo
     * @throws IOException si no se puede crear el archivo
     */
    public static void guardarArchivo(String ruta, String contenido, Boolean flagAnexarContenido) throws IOException {
        verificarRuta(ruta);
        FileWriter fw = new FileWriter(ruta, flagAnexarContenido);
        BufferedWriter bfw = new BufferedWriter(fw);
        bfw.write(contenido);
        bfw.close();
        fw.close();
    }


    /**
     * Verifica que una ruta exista, si no es asi, la crea
     *
     * @param ruta ruta que buscamos
     * @throws IOException si no se puede crear la ruta
     */
    public static void verificarRuta(String ruta) throws IOException {
        if (!existeCarpeta(ruta)) {
            crearRuta(ruta);
        }
    }

    /**
     * Crea una ruta de carpetas, se utiliza cuando el usuario
     * no tiene el sistema de archivos predefinido
     *
     * @param ruta ruta a crear
     * @throws IOException si no se puede crear la ruta
     */
    public static void crearRuta(String ruta) throws IOException {
        String[] dirs = ruta.split("\\\\");
        File folder;
        String rutaActual = "C:\\";
        for (int i = 1; i < dirs.length - 1; i++) {
            rutaActual += dirs[i] + "\\";
            folder = new File(rutaActual);
            folder.mkdirs();
        }
        folder = new File(ruta);
        folder.createNewFile();
    }

    /**
     * pasa una lista con los nombres de los archivos y carpetas en un directorio
     *
     * @param ruta ruta que queremos listar
     * @return arreglo con los nombres de los archivos y carpetas en ese directorio
     */
    public static String[] listarDir(String ruta) {
        String[] rutas;
        File f = new File(ruta);
        rutas = f.list();
        return rutas;
    }

    /**
     * determina si una carpeta de una ruta existe
     *
     * @param ruta la ruta a determinar si existe
     * @return booleano indicando si la carpeta existe o no
     */
    public static Boolean existeCarpeta(String ruta) {
        File dir = new File(ruta);
        return dir.exists();
    }

    /**
     * deja un archivo en blanco, util para evitar duplicados en la serializacion
     *
     * @param ruta ruta del archivo a limpiar
     */
    public static void limpiarArchivo(String ruta) {
        try {
            guardarArchivo(ruta, "", false);
        } catch (IOException e) {
            System.out.println("no se pudo limpiar el archivo");
            e.printStackTrace();
        }
    }

    /**
     * ESte metodo retorna el contenido del archivo ubicado en una ruta, con la lista de cadenas.
     *
     * @param ruta es la ruta o path donde está ubicado el archivo
     * @return lista de cadenas con el contenido del archivo
     * @throws IOException si no se puede leer el archivo
     */
    public static ArrayList<String> leerArchivo(String ruta) throws IOException {
        verificarRuta(ruta);

        ArrayList<String> contenido = new ArrayList<String>();
        FileReader fr = new FileReader(ruta);
        BufferedReader bfr = new BufferedReader(fr);
        String linea = "";
        while ((linea = bfr.readLine()) != null) {
            contenido.add(linea);
        }
        bfr.close();
        fr.close();
        return contenido;
    }

    /**
     * copia los contenidos del archivo en rutaOrigen  en el archivo rutaDestino,
     * utilizado a la hora de hacer respaldos de los archivos de serializacion
     *
     * @param rutaOrigen  ruta del archivo que queremos copiar
     * @param rutaDestino ruta donde queremos copiar el archivo, si este archivo no existe, es creado
     * @throws IOException si no se puede copiar el archivo
     */
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
        } catch (IOException e) {
            System.out.println("no se pudieron copiar los archivos");
            e.printStackTrace();
        } finally {
            assert is != null;
            is.close();
            assert os != null;
            os.close();
        }

    }

    //------------------------------------SERIALIZACION  y XML----------------------------------------------

    /**
     * Escribe en el fichero que se le pasa el objeto que se le envía
     *
     * @param rutaArchivo path del fichero que se quiere escribir
     * @throws IOException si no se puede escribir el archivo
     */

    public static Object cargarRecursoSerializado(String rutaArchivo) throws Exception {
        Object aux = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            // Se crea un ObjectInputStream
            aux = ois.readObject();
        }
        return aux;
    }


    /**
     * guarda la informacion de un objeto en un archivo binario
     *
     * @param rutaArchivo ruta donde guardar el archivo
     * @param object      objeto a serializar
     * @throws Exception si no se puede serializar el objeto
     */
    public static void salvarRecursoSerializado(String rutaArchivo, Object object) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(object);
        }
    }

    /**
     * metodo que permite deserializar un objeto a partir de un xml
     *
     * @param rutaArchivo ruta del archivo que queremos deserializar
     * @throws IOException si no se puede deserializar el objeto
     */


    public static Object cargarRecursoSerializadoXML(String rutaArchivo) throws IOException {
        XMLDecoder decodificadorXML;
        Object objetoXML;
        decodificadorXML = new XMLDecoder(new FileInputStream(rutaArchivo));
        objetoXML = decodificadorXML.readObject();
        decodificadorXML.close();
        return objetoXML;

    }


    /**
     * serializa un objeto en formato xml
     *
     * @param rutaArchivo ruta donde queremos guardar el serializado
     * @param objeto      objeto a serializar
     * @throws IOException si no se puede serializar el objeto
     */
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
    public static void guardar() {
        EmpresaSubasta empresaSubasta = ModelFactoryController.getInstance();
        if (empresaOutput != null) {
            guardarAux(empresaSubasta);
        } else {
            empresaOutput = new EmpresaOutput();
            guardarAux(empresaSubasta);
        }
    }

    /**
     * ESTE METODO GUARDA EL ESTADO DE LA EMPRESA PERO ANTES VERIFICA QUE
     * LA INSTANCIA DE EMPRESA OUTPUT EXISTA
     *
     * @param empresaSubasta RECIBE LA INSTANCIA A GUARDAR
     */
    private static void guardarAux(EmpresaSubasta empresaSubasta) {
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
     *
     * @return EL ESTADO DE LA EMPRESA
     */
    private static EmpresaSubasta leer() {
        if (empresaInput == null) {
            empresaInput = new EmpresaInput();
        }
        return empresaInput.leer();
    }


}
