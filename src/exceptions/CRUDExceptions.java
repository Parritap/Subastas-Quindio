package exceptions;

import model.ModelFactoryController;
import persistencia.ArchivoUtil;

import java.io.Serial;
/**
 * Esta clase es la encargada de gestionar
 * todas las excepciones posibles al momento de hacer un CRUD
 */
public class CRUDExceptions extends Exception {

	
	@Serial
	private static final long serialVersionUID = -550206058286137562L;

	static Integer nivelDeExcepcion = 2;
	//Cada excepcion toma dos mensajes, uno para imprimir en consola, otro para escribir en el log
	public CRUDExceptions(String mensaje, String mensajeLog) {
		super(mensaje);
		ArchivoUtil.guardarRegistroLog(this.getClass().getSimpleName()+", "+mensajeLog, nivelDeExcepcion, "Excepcion", ModelFactoryController.getRutaLogs("Excepciones.txt"));
	}
	
}
