package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import exceptions.CRUDExceptions;
import exceptions.EscrituraException;
import exceptions.LecturaException;
import interfaces.CRUD;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IAnuncio implements CRUD<Anuncio>, Serializable {

	//SE CAMBIA EL HASHMAP POR UN ARRAYLIST, DEBIDO A QUE SE INVIRTIO LA DEPENDENCIA DEL ID
	public ArrayList<Anuncio> listaAnuncios = new ArrayList<>();

	/**
	 * Metodo que actualiza la lista de anuncios, se actualiza
	 * con la lista contenida en el objeto pasado por parametro
	 * se filtra para no aniadir los que ya están en la lista
	 * @param iAnuncio objeto que contiene la lista de anuncios
	 */
    public void actualizarAnuncios(IAnuncio iAnuncio) {
		for (Anuncio anuncio : iAnuncio.getListaAnuncios()) {
			if (!listaAnuncios.contains(anuncio)) {
				listaAnuncios.add(anuncio);
			}
		}
    }

    //METODO QUE DEVUELVE LA LISTA ANUNCIO

	/**
	 * Método que retorna la lista de anuncios.
	 * @return Arraylist con lista de anuncios.
	 * @throws LecturaException De no haber ningún anuncio, id est, .size() = 0;
	 */
	@Override
	public ArrayList<Anuncio> listar() throws LecturaException {

		if(listaAnuncios.size() == 0){
			throw new LecturaException("No hay Anuncios para listar", "intentando acceder a una lista de anuncios vacia");
		}
		return listaAnuncios;
	}

	/**
	 * Método que buscar por el identificador del anuncio.
	 * @param id identificador a buscar dentro de la lista de anuncios.
	 * @return Anuncio con el identificador pasado por parametro.
	 * @throws LecturaException De no encontrar ningún anuncio con el identificador especificado.
	 */
	@Override
	public Anuncio buscarId(Integer id) throws LecturaException {
		for (Anuncio listaAnuncio : listaAnuncios) {
			if (listaAnuncio.compararId(id)) {
				return listaAnuncio;
			}
		}
		throw new LecturaException("No se encontró el anuncio con ese ID", "anuncio con ID "+id+" no encontrado");
	}

	/**
	 * Método que crea un anuncio haciendo antes las respectivas verificaciones de que no exista uno igual.
	 * @param anuncio Anuncio a crear.
	 * @throws EscrituraException Si ya existe igual al recibido en el parámetro.
	 */
	@Override
	public void crear(Anuncio anuncio) throws EscrituraException {
		if(existeAnuncio(anuncio)) {
			anuncio.setEstado(Estado.NUEVO);
			listaAnuncios.add(anuncio);
		}

	}
	/**
	 * METODO QUE PERMITE AGREGAR UN ANUNCIO A LA EMPRESA
	 * @param anuncio ANUNCIO A AGREGAR
	 * @throws CRUDExceptions SI NO PUEDE AGREGAR MAS ANUNCIO LANZA UNA EXCEPCION
	 */
	@Override
	public void add(Anuncio anuncio) throws CRUDExceptions {
		crear(anuncio);
	}

	/**
	 *  Método que verifica si existe un anuncio antes de crearlo
	 * @param anuncio Anuncio a comparar.
	 * @return True si el anuncio NO EXISTE
	 * @throws EscrituraException Si el anuncio ya existe.
	 */
	private boolean existeAnuncio(Anuncio anuncio) throws EscrituraException {
		for (Anuncio aux: listaAnuncios){
			if(aux.equals(anuncio)){
				return false;
			}
		}
		return true;
	}


	/**
	 * METODO QUE PERMITE ACTUALIZAR UN ANUNCIO DADO UN NUEVO
	 * ANUNCIO CON LOS NUEVOS ATRIBUTOS Y EL ID DEL ANUNCIO
	 * QUE SE VA A ACTUALIZAR
	 * @param id ID DEL ANUNCIO A ACTUALIZAR
	 * @param nuevoAnuncio ANUNCIO QUE CONTIENE LOS NUEVOS ATRIBUTOS
	 * @throws EscrituraException LANZA UNA EXCEPCION SI NO ENCUENTRA EL ANUNCIO
	 */
	@Override
	public void actualizar(Integer id, Anuncio nuevoAnuncio) throws EscrituraException {
		for(int i=0; i<listaAnuncios.size(); i++){
			if(Objects.equals(listaAnuncios.get(i).getId(), id)){
				listaAnuncios.set(i,nuevoAnuncio);
			}
		}
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
		if(!flag) throw new EscrituraException("No se ha podido eliminar el anuncio", "el anuncio con id "+id+" no se ha podido eliminar");
	}

	/**compara los elementos y ns dice si uno es mayor que el otro
	 * @param campo atributo por el cual se van a comparar los elementos
	 * @param anuncio1 anuncio a comparar
	 * @param anuncio2 anuncio a comparar
	 * */

	public static int ordenar(String campo, Anuncio anuncio1, Anuncio anuncio2) {
		int resultado = 0;

		if ("valorInicial".equals(campo)) {
			resultado = anuncio1.getValorInicial().compareTo(anuncio2.getValorInicial());
		}
		return resultado;
	}

	/**
	 * PERMITE LISTAR CON UN ORDEN
	 * @param campo ATRIBUTO POR EL QUE SE DESEA LISTAR
	 * @param dir ASCENDENTE O DESCENDENTE
	 * @return UNA LISTA CON LOS OBJETOS ORDENADOS
	 */
	@Override
	public ArrayList<Anuncio> listar(String campo, TipoOrden dir){
		ArrayList<Anuncio> listaOrdenada = listaAnuncios;
		listaOrdenada.sort((a, b) -> {
			int resultado = 0;
			if (dir == TipoOrden.ASCENDENTE) {
				resultado = ordenar(campo, a, b);
			} else if (dir == TipoOrden.DESCENDENTE) {
				resultado = ordenar(campo, b, a);
			}
			return resultado;
		});
		return listaOrdenada;
	}

	/**
	 * Devuelve la lista de anuncios almacenados en la empresa
	 * @return listaAnuncios
	 */
    public ArrayList<Anuncio> getListaAnuncio() {
		return listaAnuncios;
    }

	/**
	 * To string
	 */
	@Override
	public String toString() {
		return "Empresa{" +
				" listaAnuncios=" + listaAnuncios.toString() +
				'}';
	}
}
