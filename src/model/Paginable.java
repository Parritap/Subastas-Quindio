package model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import exceptions.CRUDExceptions;

public interface Paginable<T> {
	ArrayList<T> listar(String atributo, TipoOrden orden) throws CRUDExceptions, NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
