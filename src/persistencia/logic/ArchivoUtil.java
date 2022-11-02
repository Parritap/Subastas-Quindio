package persistencia.logic;

import model.EmpresaSubasta;
import java.io.*;
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
    //variable que contiene la fecha del sistema
    private static String fechaSistema = "";

    //-------------------------SERIALIZACION BINARIA-------------------------
    /**
     * Metodo que permite serializar un objeto en un archivo,
     * el objeto debe ser serializable
     */
    public static void serializarBinario(String rutaEmpresaSer, EmpresaSubasta instance) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaEmpresaSer))) {
            oos.writeObject(instance);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que permite deserializar un objeto en un archivo binario
     * @param rutaEmpresaSer la ruta donde se encuentra el archivo
     */

    public static Object deserializarBinario(String rutaEmpresaSer) {
        //se intenta deserializar el objeto y se retorna
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaEmpresaSer))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    //-------------------------SERIALIZACION TXT-----------------------------

    /**
     * Este metodo permite escribir en un txt dada la ruta
     * y el contenido
     * @param ruta es la ruta donde se escribirá el contenido
     * @param string es el contenido que se va a escribir
     */
    public static void serializarTxt(String ruta, String string) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            bw.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //-------------------------SERIALIZACION XML-------------------------



    //-------------------------SERIALIZACION DE LOS LOG-------------------------
    /**
     * Metodo que escribe un registro en el log de excepciones en el archivo de texto
     * @param mensajeLog mensaje de la excepcion
     * @param nivel nivel de excepcion
     * @param accion excepcion
     * @param rutaLogTxt ruta del log de excepciones
     */

    public static void guardarRegistroLog(String mensajeLog, Integer nivel, String accion, String rutaLogTxt) {
        Logger LOGGER = Logger.getLogger(accion);
        FileHandler fileHandler = null;
        cargarFechaSistema();
        try {
            fileHandler = new FileHandler(rutaLogTxt,true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            switch (nivel) {
                case 1 -> LOGGER.log(Level.INFO, accion + "," + mensajeLog + "," + fechaSistema);
                case 2 -> LOGGER.log(Level.WARNING, accion + "," + mensajeLog + "," + fechaSistema);
                case 3 -> LOGGER.log(Level.SEVERE, accion + "," + mensajeLog + "," + fechaSistema);
            }
        } catch (SecurityException | IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
            e.printStackTrace();
        } finally {
            assert fileHandler != null;
            fileHandler.close();
        }
    }

    /**
     * Metodo que actualiza la variable estática de fecha del sistema
     */
    private static void cargarFechaSistema() {
        String diaN = "";
        String mesN = "";
        Calendar cal1 = Calendar.getInstance();
        int  dia = cal1.get(Calendar.DATE);
        int mes = cal1.get(Calendar.MONTH)+1;
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
        fechaSistema = mesN+"-"+diaN;
    }

}
