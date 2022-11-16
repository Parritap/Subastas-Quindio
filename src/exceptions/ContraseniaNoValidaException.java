package exceptions;

import persistencia.logic.ArchivoUtil;
import utilities.Utils;

public class ContraseniaNoValidaException extends UsuarioException{
    public static Integer nivelDeExcepcion =2;
    //Cada excepcion toma dos mensajes, uno para imprimir en consola, otro para escribir en el log
    public ContraseniaNoValidaException(String mensaje, String mensajeLog){
        super(mensaje, mensajeLog);
        //GUARDA EN EL LOG LA EXCEPCION CON UN MENSAJE
        ArchivoUtil.guardarRegistroLog(this.getClass().getSimpleName()+", "+mensajeLog, nivelDeExcepcion,
                "Excepcion", Utils.RUTA_LOG_TXT);
    }
}
