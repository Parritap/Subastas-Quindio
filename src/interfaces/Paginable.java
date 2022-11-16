package interfaces;

import java.util.ArrayList;

import exceptions.CRUDExceptions;
import model.enums.TipoOrden;

/**
 * Esta interfaz permite obtener secciones de las listas, es decir,
 * si no queremos obtener la totalidad de clientes sino solo los primeros 20
 * que a su vez esten ordenados por un atributo y sea en orden ascendente o descendente
 * @param <T>
 */
public interface Paginable<T> {
	//Metodo encargado de paginar
	ArrayList<T> listar(String atributo, TipoOrden orden)  throws CRUDExceptions;
}
