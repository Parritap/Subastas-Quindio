package model;


import java.io.Serializable;
import java.util.ArrayList;
import exceptions.CRUDExceptions;
import exceptions.EscrituraException;
import exceptions.LecturaException;
import interfaces.CRUD;
import interfaces.IObtenerAtributo;

public class IAnuncio implements CRUD<Anuncio>, Serializable {

	//SE CAMBIA EL HASHMAP POR UN ARRAYLIST, DEBIDO A QUE SE INVIRTIO LA DEPENDENCIA DEL ID
	public static ArrayList<Anuncio> listaAnuncios = new ArrayList<>();

	//METODO QUE DEVUELVE LA LISTA ANUNCIO

	/**
	 * Método que retorna la lista de anuncios.
	 * @return Arraylist con lista de anuncios.
	 * @throws LecturaException De no haber ningún anuncio, id est, .size() = 0;
	 */
	@Override
	public ArrayList<Anuncio> listar() throws LecturaException {

		if(listaAnuncios.size() == 0){
			throw new LecturaException("No hay Anuncios para listar");
		}
		return listaAnuncios;
	}

	/**
	 * Método que buscar por el identificador del anuncio.
	 * @param id identificador a buscar dentro de la lista de anuncios.
	 * @return Anuncio con el id especificado.
	 * @throws LecturaException De no encontrar ningún anuncio con el id especificado.
	 */
	@Override
	public Anuncio buscarId(Integer id) throws LecturaException {
		for (Anuncio listaAnuncio : listaAnuncios) {
			if (listaAnuncio.compararId(id)) {
				return listaAnuncio;
			}
		}
		throw new LecturaException("No se encontró el anuncio con ese ID");
	}

	/**
	 * Método que crea un anuncio haciendo antes las respectivas verificaciones de que no exista uno igual.
	 * @param anuncio Anuncio a crear.
	 * @throws EscrituraException Si ya existe igual al recibido en el parámetro.
	 */
	@Override
	public void crear(Anuncio anuncio) throws EscrituraException {
		if(noExisteAnuncio(anuncio)) {
			anuncio.setEstado(Estado.NUEVO);
			listaAnuncios.add(anuncio);
		}

	}

	/**
	 *  Método que verifica si existe un anuncio antes de crearlo
	 * @param anuncio Anuncio a comparar.
	 * @return True si el anuncio NO EXISTE
	 * @throws EscrituraException Si el anuncio ya existe.
	 */
	private boolean noExisteAnuncio(Anuncio anuncio) throws EscrituraException {
		for (Anuncio aux: listaAnuncios){
			if(aux.equals(anuncio)) throw new EscrituraException("Ya existe un anuncio con esas características");
		}
		return true;
	}

	/**
	 * IDLE
	 */
	@Override
	public void actualizar(Integer id, Anuncio nuevoAnuncio) throws EscrituraException {

	}

	/**
	 * Elimina un anuncio con base al identificador (id) pasado en el parametro.
	 * @param id Identificador
	 * @throws EscrituraException De no encontrar y eliminar el anuncio buscado.
	 */
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

	/**
	 * IDLE
	 */
	@Override
	public ArrayList<Anuncio> listar(String campo, TipoOrden dir) throws CRUDExceptions {
		return null;
	}

	/**
	 * IDLE
	 */
	@Override
	public void add(Anuncio obj) throws CRUDExceptions {

	}

	/**
	 * MODIFICAR
	 * NOTA: Siento que esto se puede optimizar, lo veo como muy raro el método.
	 * Método retorna la lista de los anuncios ya ordenados.
	 * @param atributo
	 * @param orden
	 * @return
	 * @throws CRUDExceptions
	 */
	public ArrayList<Anuncio> listar(IObtenerAtributo atributo, TipoOrden orden) throws CRUDExceptions {
		ArrayList<Anuncio> anuncios = this.listar();
		ArrayList<Anuncio> anunciosOrdenados = new ArrayList<>();
		System.out.println(anuncios.size()); //Imprime la cantidad de anuncios.

		//índice en el que vamos a insertar cada elemento al ordenar
		int ind = 0;
		for (Anuncio anuncio : anuncios) {
			for (int j = 0; j < anunciosOrdenados.size(); j++) {
				ind = j;
				if (orden == TipoOrden.ASCENDENTE && !esMayorA(atributo.obtenerAttr(anuncio)
						, atributo.obtenerAttr(anunciosOrdenados.get(j)))) break; //Un break omg!
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
