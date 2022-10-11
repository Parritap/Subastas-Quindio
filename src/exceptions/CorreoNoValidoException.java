package exceptions;

import model.ModelFactoryController;
import persistencia.ArchivoUtilLog;

public class CorreoNoValidoException extends UsuarioException{
    public static Integer nivelDeExcepcion =2;
    //Cada excepcion toma dos mensajes, uno para imprimir en consola, otro para escribir en el logs
    public CorreoNoValidoException(String mensaje, String mensajeLog){
        super(mensaje, mensajeLog);
        ArchivoUtilLog.guardarRegistroLog(this.getClass().getSimpleName()+", "+mensajeLog, nivelDeExcepcion, "Excepcion", ModelFactoryController.getRutaLogs("Excepciones.txt"));
    }
}
