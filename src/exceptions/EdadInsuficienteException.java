package exceptions;

import model.ModelFactoryController;
import persistencia.ArchivoUtil;

public class EdadInsuficienteException extends UsuarioException{
    public static Integer nivelDeExcepcion = 2;
    //Cada excepcion toma dos mensajes, uno para imprimir en consola, otro para escribir en el log
    public EdadInsuficienteException(String mensaje, String mensajeLog) {
        super(mensaje,mensajeLog);
        ArchivoUtil.guardarRegistroLog(this.getClass().getSimpleName()+", "+mensajeLog, nivelDeExcepcion, "Excepcion", ModelFactoryController.getRutaLogs("Excepciones.txt"));
    }
}
