package model;

import persistencia.Persistencia;
import utilities.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * SINGLETON
 */
public class ModelFactoryController {

    //VARIABLE GENERAL PARA TODA LA EMPRESA
    private static EmpresaSubasta empresaSubasta;
    private static String rutaLogs;
    private static Integer idListaPujas=0;
    /**
     * METODO QUE DEVUELVE LA INSTANCIA DE LA EMPRESA
     * @return LA INSTANCIA DE LA EMPRESA
     */
    public static EmpresaSubasta getInstance(){
        return Objects.requireNonNullElseGet(empresaSubasta, () -> empresaSubasta = new EmpresaSubasta());
    }

    public static void deserializarEmpresa(){
        empresaSubasta  = Persistencia.deserializarEmpresaXML();
        //hace una copia de seguridad del xml
        Persistencia.respaldarXML();
    }

    public static IUsuario getIUsuario(){
        return getInstance().getIUsuario();
    }

    public static void agregarUsuario(Usuario ur){}


    //Los metodos getRuta... sirven para obtener las rutas
    //especificadas en el taller

    /**devuelve la ruta en la que se guarda el log
     * @param nombreArchivo nombre del archivo en el que se guarda el log
     * @return ruta en la que se va a guardar el log*/
    public static String getRutaLogs(String nombreArchivo){
        return "C:\\td\\persistencia\\log\\"+nombreArchivo;
    }

    public static String getRutaObjetos(String nombreArchivo){
        return "C:\\td\\persistencia\\archivos\\"+nombreArchivo;
    }

    //CONSTRUYE LA RUTA EN BASE AL NOMBRE DE LA CLASE
    public static String getRutaObjetos(Object obj){
        Class<?> claseObj = obj.getClass();
        return "C:\\td\\persistencia\\archivos\\"+claseObj.getSimpleName()+".txt";
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
        return "C:\\td\\persistencia\\respaldo\\"+nombre+"__"+f.getDayOfMonth()+f.getMonthValue()+(f.getYear()%100)+
                "__"+f.getHour()+"__"+f.getMinute()+"__"+f.getSecond()+"."+formato;
    }

    public static String getRutaSerializado(String nombreArchivo){
        return "C:\\td\\"+nombreArchivo;
    }

    /**da un id para la lista de pujas, usualmente a objetos Usuario o Anuncio
     * este es necesario para la deserializacion
     * */
    public static Integer darIdListaPuja(){
        idListaPujas++;
        return idListaPujas;
    }
}
