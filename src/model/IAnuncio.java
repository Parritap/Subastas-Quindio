package model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

	/**
	 * Metodo que actualiza un elemento de la listAnuncios
	 * determinado por una id
	 * @param id identificador o codigo del elemento que se busca
	 * @param nuevoAnuncio anuncio por el cual vamos a reemplazar el anuncio viejo
	 * */
	@Override
	public void actualizar(Integer id, Anuncio nuevoAnuncio) throws EscrituraException {
		for(int i=0; i<listaAnuncios.size(); i++){
			if(listaAnuncios.get(i).getId() == id){
				listaAnuncios.set(i,nuevoAnuncio);
			}
		}
	}

	/**
	 * Elimina un anuncio de la lista de anuncios
	 * @param id id del elemento que se desa eliminar*/
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

	/**compara los elementos y ns dice si uno es mayor que el otro*/
	public static int ordenar(String campo, Anuncio anuncio1, Anuncio anuncio2) {
		int resultado = 0;

		switch (campo) {
			case "nombreAnunciante" -> resultado = anuncio1.getNombreAnunciante().compareTo(anuncio2.getNombreAnunciante());
			case "tiempoActivo" -> resultado = anuncio1.getTiempoActivo().compareTo(anuncio2.getTiempoActivo());
			case "valorInicial" -> resultado = anuncio1.getValorInicial().compareTo(anuncio2.getValorInicial());
		}
		return resultado;
	}

	/**lista los elementos de manera ordenada comparandolos mediante un atributo "campo"
	 * @param campo atributo por el cual se comparan los elemntos
	 * @param dir forma en la que se ordenan, ascendente o descendente*/
	@Override
	public ArrayList<Anuncio> listar (String campo, TipoOrden dir) throws CRUDExceptions {
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



	//metodo alternativo para listar utilizando reflexion y los getters de Lombok
	public ArrayList<Anuncio> listar(String campo, TipoOrden dir, Comparar comparar) throws CRUDExceptions, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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
	 * agrega un anuncio a la lisa, pero primero
	 * verifica que no se haya creado antes
	 * @param anuncio anuncio a agregar
	 * */
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
	/**ordena los objetos utilizando un lambda "criterio", bien sea por un atributo o al
	aplicar una operacion
	 @param criterio lambda utilizado para determinar cuando un elemento es mayor al otro
	 @param orden enumeracion utilizada para saber si se es en orden ascendente o descendente.
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