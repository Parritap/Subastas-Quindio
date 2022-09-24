package model;

import java.util.ArrayList;

import exceptions.CRUDExceptions;

public interface Paginable<T> {
	ArrayList<T> listar(String atributo, TipoOrden orden)  throws CRUDExceptions; 
}
