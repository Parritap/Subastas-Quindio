package interfaces;

import java.util.ArrayList;
import exceptions.CRUDExceptions;
import model.enums.TipoOrden;

/**
 * Esta clase se encarga de implementar los crud en una
 * clase implementación
 * @param <T> se debe definir un tipo T que es dedicado solo a eso,
 *  Algunos ejemplos de tipo T pueden ser Cliente, Producto, etc
 */
public interface CRUD<T> extends Ordenable<T>, Paginable<T> {

	/**
	 * MÉTODO QUE PERMITE BUSCAR UN OBJETO DADO SU ID
	 * @param id por el que se va a buscar
	 * @return El objeto tipo T que contiene ese ID
	 */
	T buscarId(Integer id) throws CRUDExceptions; 
	
	/**
	 * Metodo que permite crear un objeto de tipo T
	 * @param obj La acción de crear es añadirlo a las listas de las
	 * implementaciones, pero antes se debe verificar que no exista un T con las mismas
	 *            caracteristicas
	 */
	void crear(T obj) throws CRUDExceptions;

	/**
	 * Metodo que permite ordenar una lista auxiliar de la lista principal
	 * se puede ordenar la lista por un atributo y ordenar en una dirección
	 * puede ser ascendente o descendente
	 * @param atributo por el que se van a ordenar los objetos
	 * @param dir Ascendente o descendente
	 * @return una lista ordenada según las condiciones
	 * @throws CRUDExceptions se puede lanzar una excepción por alguna lectura
	 */
	ArrayList<T> listar(String atributo, TipoOrden dir) throws CRUDExceptions;

	/**
	 * Metodo que permite añadir un objeto de tipo T
	 * @param obj La acción de crear es añadirlo a las listas de las implementaciones,
	 * @throws CRUDExceptions se puede lanzar una excepción por alguna lectura
	 */
	void add(T obj) throws CRUDExceptions;

}
