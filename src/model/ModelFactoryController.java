package model;

import exceptions.LecturaException;
import persistencia.logic.Persistencia;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import exceptions.CRUDExceptions;
import exceptions.EscrituraException;
import java.util.Objects;

/**
 * SINGLETON
 */
public class ModelFactoryController {

    //VARIABLE GENERAL PARA TODA LA EMPRESA
    private static EmpresaSubasta empresaSubasta;
    private static Integer idListaPujas=0;
    /**
     * METODO QUE DEVUELVE LA INSTANCIA DE LA EMPRESA
     * @return LA INSTANCIA DE LA EMPRESA
     */
    public static EmpresaSubasta getInstance(){
        try {
            deserializarEmpresa();
        } catch (CRUDExceptions e) {
            throw new RuntimeException(e);
        }
        return Objects.requireNonNullElseGet(empresaSubasta, () -> {
            try {
                return empresaSubasta = new EmpresaSubasta();
            } catch (CRUDExceptions e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void deserializarEmpresa() throws CRUDExceptions {
        empresaSubasta  = Persistencia.deserializarEmpresaXML();
        //hace una copia de seguridad del xml
        Persistencia.respaldarXML();
    }

    //Los metodos getRuta... sirven para obtener las rutas
    //especificadas en el taller

    /**
     * Método que Retorna el siguiente string: C:\Users\esteb\IdeaProjects\Subastoncito\src
     * @return "C:\Users\esteb\IdeaProjects\Subastoncito\src".
     */
    public static String getRutaBase(){
        return Paths.get("").toAbsolutePath().toString()+"\\src";
    }

    /**
     * Devuelve la ruta del log de las excepciones.
     * @return ruta de log de excepciones.
     */
    public static String getRutaLogException(){
        return getRutaBase()+"\\persistencia\\exceptions\\registroExcepciones.log";
    }

    public static String getRutaLogAcciones (){
        return getRutaBase()+"\\persistencia\\exceptions\\registroAcciones.log";
    }

    /**devuelve la ruta en la que se guarda el log
     * @param nombreArchivo nombre del archivo en el que se guarda el log
     * @return ruta en la que se va a guardar el log*/
    public static String getRutaLogs(String nombreArchivo){
        return getRutaBase()+"\\persistencia\\log\\"+nombreArchivo;
    }

    public static String getRutaRegistroAcciones (){
        return getRutaBase()+"\\persistencia\\log\\Acciones.log";
    }

    public static String getRutaObjetos(String nombreArchivo){
        return getRutaBase()+"\\persistencia\\archivos\\"+nombreArchivo;
    }

    //CONSTRUYE LA RUTA EN BASE AL NOMBRE DE LA CLASE
    public static String getRutaObjetos(Object obj){
        Class<?> claseObj = obj.getClass();
        return getRutaBase()+"\\persistencia\\archivos\\"+claseObj.getSimpleName()+".txt";
    }

    /**devuelve la ruta de respaldo con el formato que debe tener
     * el nombre del archivo
     * */

    public static String getRutaRespaldo(String nombreArchivo){
        String[] componentes = nombreArchivo.split("\\.");
        String nombre  = componentes[0];
        String formato="";
        if(componentes.length > 1) {
            formato = componentes[1];
        }
        LocalDateTime f = LocalDateTime.now();
        return  getRutaBase()+"\\persistencia\\respaldo\\"+nombre+"__"+f.getDayOfMonth()+f.getMonthValue()+(f.getYear()%100)+
                "__"+f.getHour()+"__"+f.getMinute()+"__"+f.getSecond()+"."+formato;
    }

    public static String getRutaSerializado(String nombreArchivo){
        return  getRutaBase()+"\\persistencia\\"+nombreArchivo;
    }

    /**da un ID para la lista de pujas, usualmente a objetos Usuario o Anuncio
     * este es necesario para deserializar
     * */
    public static Integer darIdListaPuja(){
        idListaPujas++;
        return idListaPujas;
    }
    public static ArrayList<Anuncio> getlistaAnuncios() {
        return getInstance().getListaAnuncios();
    }

    public static void crearUsuario(Usuario usuario) throws EscrituraException, IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        //serializa el usuario
        Persistencia.serializarUsuario(usuario);
        empresaSubasta.crearUsuario(usuario);
    }

    public static void crearAnuncio(Anuncio anuncio, Producto producto, Usuario clienteActivo) throws CRUDExceptions, IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        empresaSubasta.crearAnuncio(anuncio, producto, clienteActivo);
        Persistencia.serializarAnuncio(anuncio);
    }

    public static void actualizarUsuario(Usuario clienteActivo, Usuario usuario) throws LecturaException {
        empresaSubasta.actualizarUsuario(clienteActivo, usuario);
    }
}
