package exceptions;

import model.ModelFactoryController;
import persistencia.logic.ArchivoUtil;

/**
 * Clase encargada de lanzar una excepcion de tipo anuncio exception
 */
public class AnuncioException  extends  EmpresaException{

    //Gravedad de la excepcion: WARNING
    static Integer nivelDeExcepcion = 2;
    //Cada excepcion toma dos mensajes, uno para imprimir en consola, otro para escribir en el log
    public AnuncioException (String mensaje,  String mensajeLog){

        super(mensaje, mensajeLog);
        //guarda el log, utiliza el nombre de la clase para indicarlo en el log                                              //todas las excepciones se guardan en Excepcion.txt
        ArchivoUtil.guardarRegistroLog(this.getClass().getSimpleName()+", "+mensajeLog, nivelDeExcepcion, "Excepcion", ModelFactoryController.getRutaLogs("Excepciones.txt"));
    }
}
