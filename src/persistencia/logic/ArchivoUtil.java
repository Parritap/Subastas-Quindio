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
     * Metodo que permite serializar un objeto en un archivo
     */
    public static void serializarBinario(String rutaEmpresaSer, EmpresaSubasta instance) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaEmpresaSer))) {
            oos.writeObject(instance);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //-------------------------DESERIALIZATION BINARIA-------------------------

    /**
     * Metodo que permite deserializar un objeto en un archivo
     */

    public static Object deserializarBinario(String rutaEmpresaSer) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaEmpresaSer))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

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
}
