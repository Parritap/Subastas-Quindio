package model;

import java.util.ArrayList;

import exceptions.CRUDExceptions;

public interface Ordenable<T> {
	ArrayList<T> listar(String atributo, TipoOrden orden)  throws CRUDExceptions; 
}
