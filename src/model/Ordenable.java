package model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import exceptions.CRUDExceptions;

public interface Ordenable<T> {
	//cambie uno de los parametros para obtener el valor del atributo
	//mediante un lambda
	 ArrayList<T> listar(String campo, TipoOrden dir) throws CRUDExceptions, NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
