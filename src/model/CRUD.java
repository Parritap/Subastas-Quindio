package model;

import java.util.ArrayList;
import exceptions.CRUDExceptions;

public interface CRUD<T> extends Ordenable<T>, Paginable<T> {
	
	
	/**
	 * MÉTODO QUE DEVUELVE UNA LISTA DE OBJETO DE TIPO T
	 * @return
	 */
	ArrayList<T> listar() throws CRUDExceptions; 
	
	/**
	 * MÉTODO QUE PERMITE BUSCAR UN OBJETO DADO SU ID
	 * @param id
	 * @return
	 */
	T buscarId(Integer id) throws CRUDExceptions; 
	
	/**
	 * MÉTODO QUE PERMITE CREAR UN OBJETO DE TIPO T
	 * @param obj
	 */
	void crear(T obj) throws CRUDExceptions; 
	
	/**
	 * MÉTODO QUE PERMITE CREAR UN OBJETO DE TIPO T
	 * @param obj
	 */
	void actualizar(T obj)  throws CRUDExceptions; 
	/**
	 * MÉTODO QUE PERMITE ELIMINAR UN OBJETO DE TIPO T
	 * @param obj
	 */
	void Eliminar(T obj)  throws CRUDExceptions; 
}
