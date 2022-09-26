package model;

import java.util.ArrayList;

import exceptions.CRUDExceptions;

public interface Ordenable<T> {
	//cambie uno de los parametros para obtener el valor del atributo
	//mediante un lambda
	ArrayList<T> listar(IObtenerAtributo atributo, TipoOrden orden)  throws CRUDExceptions; 
}
