package exceptions;

import java.io.Serial;
/**
 * Esta clase es la encargada de gestionar
 * todas las excepciones posibles al momento de hacer un CRUD
 */
public class CRUDExceptions extends Exception {

	
	@Serial
	private static final long serialVersionUID = -550206058286137562L;
	
	
	public CRUDExceptions(String mensaje) {
		super(mensaje);
	}
	
}
