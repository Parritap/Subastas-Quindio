package exceptions;

import persistencia.logic.ArchivoUtil;
import utilities.Utils;

public class LecturaException extends CRUDExceptions{
    public static Integer nivelDeExcepcion =2;
    //Cada excepcion toma dos mensajes, uno para imprimir en consola, otro para escribir en el log
    public LecturaException(String mensaje,  String mensajeLog) {
        super(mensaje, mensajeLog);
        ArchivoUtil.guardarRegistroLog(this.getClass().getSimpleName()+", "+mensajeLog, nivelDeExcepcion, "Excepcion", Utils.RUTA_LOG_TXT);
    }
}
