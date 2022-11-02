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
	 * Metodo que devuelve un arrayList del tipo T
	 * @return un Arraylist que contiene el total de elementos
	 */
	ArrayList<T> listar() throws CRUDExceptions; 
	
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
	 * Metodo que permite actualizar un objeto de tipo T
	 * @param obj contiene todos los atributos que se van a actualizar
	 * @param id es id con el que se va a buscar el objeto a actualizar
	 */
	void actualizar(Integer id,T obj)  throws CRUDExceptions;


	/**
	 * Metodo que permite eliminar un elemento de tipo T dado su id
	 * @param id por el que se va a eliminar el objeto
	 */
	// aquí también hice un cambio
	void Eliminar(Integer id)  throws CRUDExceptions;

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
	 * @param obj
	 * @throws CRUDExceptions
	 */
	void add(T obj) throws CRUDExceptions;

}
