package persistencia.logic;

import model.EmpresaSubasta;
import java.io.*;

/**
 * ESTA CLASE SIRVE COMO AYUDA PARA LA LECTURA Y ESCRITURA DE LOS ESTADOS
 * DE LA EMPRESA
 */
public class ArchivoUtil {

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
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaEmpresaSer))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    //-------------------------SERIALIZACION TXT-------------------------






    //-------------------------SERIALIZACION XML-------------------------








    //-------------------------SERIALIZACION DE LOS LOG-------------------------
    /**
     * Metodo que escribe un registro en el log de excepciones en el archivo de texto
     * @param s mensaje de la excepcion
     * @param nivelDeExcepcion nivel de excepcion
     * @param excepcion excepcion
     * @param rutaLogTxt ruta del log de excepciones
     */

    public static void guardarRegistroLog(String s, Integer nivelDeExcepcion, String excepcion, String rutaLogTxt) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaLogTxt, true))) {
            bw.write(s + ", " + nivelDeExcepcion + ", " + excepcion);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
}
