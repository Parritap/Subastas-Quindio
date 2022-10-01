package interfaces;

import java.util.ArrayList;

import exceptions.CRUDExceptions;
import model.TipoOrden;

public interface Paginable<T> {
	ArrayList<T> listar(String atributo, TipoOrden orden)  throws CRUDExceptions;
}
