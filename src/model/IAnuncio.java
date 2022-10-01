package model;


import java.io.Serializable;
import java.util.ArrayList;
import exceptions.CRUDExceptions;
import exceptions.EscrituraException;
import exceptions.LecturaException;

public class IAnuncio implements CRUD<Anuncio>, Serializable {

	//SE CAMBIA EL HASHMAP POR UN ARRAYLIST, DEBIDO A QUE SE INVIRTIO LA DEPENDENCIA DEL ID
	public static ArrayList<Anuncio> listaAnuncios = new ArrayList<>();

	//METODO QUE DEVUELVE LA LISTA ANUNCIO
	@Override
	public ArrayList<Anuncio> listar() throws LecturaException {

		if(listaAnuncios.size() == 0){
			throw new LecturaException("No hay Anuncios para listar");
		}
		return listaAnuncios;
	}

	//METODO QUE BUSCA POR EL ID, SI NO LO ENCUENTRA LANZA UNA EXCEPCION
	@Override
	public Anuncio buscarId(Integer id) throws LecturaException {
		for (Anuncio listaAnuncio : listaAnuncios) {
			if (listaAnuncio.compararId(id)) {
				return listaAnuncio;
			}
		}
		throw new LecturaException("No se encontró el anuncio con ese ID");
	}

	//METODO QUE CREA UN ANUNCIO PERO ANTES VERIFICA SI EXISTE, SI EXISTE LO CREA SI NO LANZA UNA EXCEPCION
	@Override
	public void crear(Anuncio anuncio) throws EscrituraException {
		if(noExisteAnuncio(anuncio)) {
			anuncio.setEstado(Estado.NUEVO);
			listaAnuncios.add(anuncio);
		}

	}

	//METODO QUE VERIFICA SI EXISTE UN ANUNCIO ANTES DE CREARLO
	private boolean noExisteAnuncio(Anuncio anuncio) throws EscrituraException {
		for (Anuncio aux: listaAnuncios){
			if(aux.equals(anuncio)) throw new EscrituraException("Ya existe un anuncio con esas caracteristicas");
		}
		return true;
	}

	@Override
	public void actualizar(Integer id, Anuncio nuevoAnuncio) throws EscrituraException {

	}

	@Override
	public void Eliminar(Integer id) throws EscrituraException {
		boolean flag = false;
		for (Anuncio listaAnuncio : listaAnuncios) {
			if (listaAnuncio.compararId(id)) {
				listaAnuncio.setEstado(Estado.ELIMINADO);
				flag = true;
			}
		}
		if(!flag) throw new EscrituraException("No se ha podido eliminar el anuncio");
	}

	@Override
	public ArrayList<Anuncio> listar(String campo, TipoOrden dir) throws CRUDExceptions {
		return null;
	}

	@Override
	public void add(Anuncio obj) throws CRUDExceptions {

	}


	public ArrayList<Anuncio> listar(IObtenerAtributo atributo, TipoOrden orden) throws CRUDExceptions {
		ArrayList<Anuncio> anuncios = this.listar();
		ArrayList<Anuncio> anunciosOrdenados = new ArrayList<>();
		System.out.println(anuncios.size());
		//indice en el que vamos a insertar cada elemento al ordenar
		int ind = 0;
		for (Anuncio anuncio : anuncios) {
			for (int j = 0; j < anunciosOrdenados.size(); j++) {
				ind = j;
				if (orden == TipoOrden.ASCENDENTE && !esMayorA(atributo.obtenerAttr(anuncio)
						, atributo.obtenerAttr(anunciosOrdenados.get(j)))) break;
				if (orden == TipoOrden.DESCENDENTE && esMayorA(atributo.obtenerAttr(anuncio)
						, atributo.obtenerAttr(anunciosOrdenados.get(j)))) break;
			}
			if (ind >= anunciosOrdenados.size() - 1) anunciosOrdenados.add(anuncio);
			anunciosOrdenados.add(ind, anuncio);
		}

		return anunciosOrdenados;
	}

	//es necesario crear mas de estos para cada tipo de los
	//atributos de anuncio, LocalDate, String, etc...
	public Boolean esMayorA(Integer valor1, Integer valor2) {
		return valor1 > valor2;
	}

	public Boolean esMayorA(Double valor1, Double valor2) {
		return valor1 > valor2;
	}

}
