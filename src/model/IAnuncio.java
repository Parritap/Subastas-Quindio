package model;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import exceptions.CRUDExceptions;

public class IAnuncio implements CRUD<Anuncio> {
	//cada anuncio esta relacionado a un id de tipo entero
	//ya que solo vamos a manejar una lista de anuncios podemos hacerlo static
	public static HashMap<Integer, Anuncio> listaAnuncios =
			new HashMap<>();
	//cada vez que se crea un anuncio se le pone como id el
	//valor de esta variable, la cual va aumentar cada vez
	//que creemos un anuncio
	static Integer idDisponible = 000;

	@Override
	public ArrayList<Anuncio> listar() throws CRUDExceptions {
		//crea un arraylist que tendra lo que retorna la funcion
		ArrayList<Anuncio> resultado = new ArrayList<>();
		//crea un iterador para recorrer el conjunto de llaves
		Iterator<Integer> iterador = listaAnuncios.keySet().iterator();

		//recorre el set de llaves
		while (iterador.hasNext()) {
			resultado.add(listaAnuncios.get(iterador.next()));
		}
		return resultado;
	}


	@Override
	public Anuncio buscarId(Integer id) throws CRUDExceptions {
		return listaAnuncios.get(id);
	}

	@Override
	public void crear(Anuncio anuncio) throws CRUDExceptions {
		IAnuncio.listaAnuncios.put(IAnuncio.idDisponible, anuncio);
		//se aumenta en 1 para no repetir id's
		IAnuncio.idDisponible++;
	}

	@Override
	public void actualizar(Integer id, Anuncio nuevoAnuncio) throws CRUDExceptions {
		listaAnuncios.put(id, nuevoAnuncio);
	}

	@Override
	public void Eliminar(Integer id) throws CRUDExceptions {
		listaAnuncios.remove(id);
	}

	@Override
	public ArrayList<Anuncio> listar(String campo, TipoOrden dir) throws CRUDExceptions {
		return null;
	}


	public ArrayList<Anuncio> listar(IObtenerAtributo atributo, TipoOrden orden) throws CRUDExceptions {
		ArrayList<Anuncio> anuncios = this.listar();
		ArrayList<Anuncio> anunciosOrdenados = new ArrayList<>();
		System.out.println(anuncios.size());
		//indice en el que vamos a insertar cada elemento al ordenar
		int ind = 0;
		for (int i = 0; i < anuncios.size(); i++) {
			for (int j = 0; j < anunciosOrdenados.size(); j++) {
				ind = j;
				if (orden == TipoOrden.ASCENDENTE && !esMayorA(atributo.obtenerAttr(anuncios.get(i))
						, atributo.obtenerAttr(anunciosOrdenados.get(j)))) break;
				if (orden == TipoOrden.DESCENDENTE && esMayorA(atributo.obtenerAttr(anuncios.get(i))
						, atributo.obtenerAttr(anunciosOrdenados.get(j)))) break;
			}
			if (ind >= anunciosOrdenados.size() - 1) anunciosOrdenados.add(anuncios.get(i));
			anunciosOrdenados.add(ind, anuncios.get(i));
		}

		return anunciosOrdenados;
	}

	//es necesario crear mas de estos para cada tipo de los
	//atributos de anuncio, LocalDate, Strin, etc... 
	public Boolean esMayorA(Integer valor1, Integer valor2) {
		return valor1 > valor2;
	}

	public Boolean esMayorA(Double valor1, Double valor2) {
		return valor1 > valor2;
	}

}
