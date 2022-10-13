package model;

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

    public static IUsuario getIUsuario(){
        return getInstance().getIUsuario();
    }

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
    public static String getRutaRespaldoArchivo(String nombreArchivo){
        LocalDateTime f = LocalDateTime.now();
        //return getRutaRespaldo()+nombreArchivo+"__"+f.getDayOfMonth()+f.getMonth()+(f.getYear()%100)+
                //"__"+f.getHour()+"__"+f.getMinute()+"__"+f.getSecond();
        return "";
    }

    public static String getRutaRespaldo(String nombreArchivo){
        LocalDateTime f = LocalDateTime.now();
        return "C:\\td\\persistencia\\respaldo\\"+nombreArchivo+"__"+f.getDayOfMonth()+f.getMonth()+(f.getYear()%100)+
                "__"+f.getHour()+"__"+f.getMinute()+"__"+f.getSecond();
    }
    public static Integer darIdListaPuja(){
        idListaPujas++;
        return idListaPujas;
    }
}
