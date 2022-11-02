package interfaces;

import exceptions.CRUDExceptions;
import model.enums.TipoOrden;
import java.util.ArrayList;

/**
 * Esta interfaz permite ordenar una lista por un atributo y un tipo
 * de orden, ya sea ascendente o descendente
 * @param <T> Tipo por el que se va a ordenar
 */
public interface Ordenable<T> {
    //cambie uno de los parametros para obtener el valor del atributo
    //mediante un operador lambda
    ArrayList<T> listar(String campo, TipoOrden dir)  throws CRUDExceptions;
}
