package model;


import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
			if(listaAnuncios.get(i).getId() == id){
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
		if(!flag) throw new EscrituraException("No se ha podido eliminar el anuncio");
	}

	/**compara los elementos y ns dice si uno es mayor que el otro
	 * @param campo atributo por el cual se van a comparar los elementos
	 * @param anuncio1 anuncio a comparar
	 * @param anuncio2 anuncion a comparar
	 * */

	public static int ordenar(String campo, Anuncio anuncio1, Anuncio anuncio2) {
		int resultado = 0;

		switch (campo) {
			case "nombreAnunciante" -> resultado = anuncio1.getNombreAnunciante().compareTo(anuncio2.getNombreAnunciante());
			case "tiempoActivo" -> resultado = anuncio1.getTiempoActivo().compareTo(anuncio2.getTiempoActivo());
			case "valorInicial" -> resultado = anuncio1.getValorInicial().compareTo(anuncio2.getValorInicial());
		}
		return resultado;
	}

	/**
	 * PERMITE LISTAR CON UN ORDEN
	 * @param campo ATRIBUTO POR EL QUE SE DESEA LISTAR
	 * @param dir ASCENDENTE O DESCENDENTE
	 * @return UNA LISTA CON LOS OBJETOS ORDENADOS
	 * @throws CRUDExceptions PUEDEN SUCEDER ERRORES DE LECTURA
	 * @throws CRUDExceptions PUEDEN SUCEDER ERRORES DE LECTURA
	 */


	@Override
	public ArrayList<Anuncio> listar(String campo, TipoOrden dir) throws CRUDExceptions {
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


	/**metodo alternativo para listar utilizando reflexion y los getters de Lombok
	 * @param campo nombre del atributo por el cual vamos a comparar los anuncios
	 * @param dir si se ordena de anera ascendente o descendente
	 * @param comparar lambda con la funcion por la cual vamos a comparar los valores del atributo*/
	public ArrayList<Anuncio> listar(String campo, TipoOrden dir, Comparar comparar) throws CRUDExceptions, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InvocationTargetException {
		String nombreMetodo ="get"+Character.toUpperCase(campo.charAt(0))+campo.substring(1);
		Method getter = Anuncio.class.getDeclaredMethod(nombreMetodo);
		ArrayList<Anuncio> anuncios = this.listar();
		ArrayList<Anuncio> anunciosOrdenados = new ArrayList<>();
		//indice en el que vamos a insertar cada elemento al ordenar
		int ind = 0;
		Boolean esMayor;
		for (Anuncio anuncio : anuncios) {
			for (int j = 0; j < anunciosOrdenados.size(); j++) {
				ind = j;
				esMayor = comparar.esMayor(getter.invoke(anuncio), getter.invoke(anunciosOrdenados.get(j)));
				if (dir == TipoOrden.ASCENDENTE && !esMayor) break;
				if (dir == TipoOrden.DESCENDENTE && esMayor) break;
			}
			if (ind >= anunciosOrdenados.size() - 1) anunciosOrdenados.add(anuncio);
			anunciosOrdenados.add(ind, anuncio);
		}
		return anunciosOrdenados;
	}

	/**
	 * METODO QUE PERMITE AGREGAR UN ANUNCIO A LA EMPRESA
	 * @param anuncio ANUNCIO A AGREGAR
	 * @throws CRUDExceptions SI NO PUEDE AGREGAR MAS ANUNCIO LANZA UNA EXCEPCION
	 */
	@Override
	public void add(Anuncio anuncio) throws CRUDExceptions {
		if (noExisteAnuncio(anuncio)) {
			listaAnuncios.add(anuncio);
		}
	}


	/**Agrega N anuncios a la lista
	 toma N argumentos
	 @param anuncios una lista de argumentos que son los anuncios a agregar
	 */
	public void crearN(Anuncio... anuncios) throws LecturaException, EscrituraException {
		for(Anuncio anuncio: anuncios){
			if(noExisteAnuncio(anuncio)){
				anuncio.setEstado(Estado.NUEVO);
				listaAnuncios.add(anuncio);
			}
		}
	}


	/**
	 * METODO QUE PERMITE ORDENAR DADO UN ATRIBUTO Y UN ORDEN
	 * @param criterio LAMBDA CON EL CRITETIO POR EL QUE SE DESEA ORDENAR
	 * @param orden ASCENDENTE O DESCENDENTE
	 * @return UN ARRAYLIST CON LOS OBJETOS ORDENADOS
	 * @throws CRUDExceptions PUEDEN OCURRIR ERRORES DE LECTURA
	 */

	public ArrayList<Anuncio> listar(Comparar criterio, TipoOrden orden) throws CRUDExceptions {
		ArrayList<Anuncio> anuncios = this.listar();
		ArrayList<Anuncio> anunciosOrdenados = new ArrayList<>();
		//indice en el que vamos a insertar cada elemento al ordenar
		int ind = 0;
		for (Anuncio anuncio : anuncios) {
			for (int j = 0; j < anunciosOrdenados.size(); j++) {
				ind = j;
				if (orden == TipoOrden.ASCENDENTE && !criterio.esMayor(anuncio, anunciosOrdenados.get(j))) break;
				if (orden == TipoOrden.DESCENDENTE && criterio.esMayor(anuncio, anunciosOrdenados.get(j))) break;
			}
			if (ind >= anunciosOrdenados.size() - 1) anunciosOrdenados.add(anuncio);
			anunciosOrdenados.add(ind, anuncio);
		}

		return anunciosOrdenados;
	}

}
