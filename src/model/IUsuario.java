package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import exceptions.CRUDExceptions;

//los mismos metodos y atributos que IAnuncio
//pero para Usuarios
public class IUsuario implements CRUD<Usuario>{
	public static HashMap<Integer, Usuario> listaUsuarios = 
			new HashMap<>();
	public static Integer idDisponible = 000;


	@Override
	public ArrayList<Usuario> listar() throws CRUDExceptions {
		ArrayList<Usuario> resultado = new ArrayList<>();

		for (Integer integer : listaUsuarios.keySet()) {
			resultado.add(listaUsuarios.get(integer));
		}
		return resultado;
	}

	@Override
	public Usuario buscarId(Integer id) throws CRUDExceptions {
		return listaUsuarios.get(id);
	}

	@Override
	public void crear(Usuario usr) throws CRUDExceptions {
		IUsuario.listaUsuarios.put(IUsuario.idDisponible, usr);
		IUsuario.idDisponible++;
	}

	@Override
	public void actualizar(Integer id, Usuario nuevoUsr) throws CRUDExceptions {
		listaUsuarios.put(id, nuevoUsr);
		
	}

	@Override
	public void Eliminar(Integer id) throws CRUDExceptions {
		listaUsuarios.remove(id);
	}

	/**
	 * Este método permite ordenar por un atributo y el tipo de orden,
	 * ya sea ascendente o descendete
	 * @return un ArrayList con los elementos ordenados
	 */
	@Override
	public ArrayList<Usuario> listar(String campo, TipoOrden dir) {
		ArrayList<Usuario> listaOrdenada = obtenerListaUsuarios();
		listaOrdenada.sort((a, b) -> {
			int resultado = 0;
			if(dir == TipoOrden.ASCENDENTE){
				resultado = ordenar(campo, a, b);
			} else if(dir == TipoOrden.DESCENDENTE){
				resultado = ordenar(campo, b, a);
			}
			return resultado;
		});
		return listaOrdenada;
	}

	/**
	 * Este metodo me devuelve todos los elementos contenidos en el hashMap
	 */
	private ArrayList<Usuario> obtenerListaUsuarios() {
		ArrayList<Usuario> usuarios = new ArrayList<>();

		for (int i=0; i<idDisponible; i++){
			usuarios.add(listaUsuarios.get(i));
		}
		return usuarios;
	}


	public static int ordenar(String campo, Usuario a, Usuario b){
		int resultado = 0;

		switch (campo){
			case "edad" ->
					resultado = a.getAge().compareTo(b.getAge());
			case "nombre" ->
					resultado = a.getName().compareTo(b.getName());
			case "cedula" ->
					resultado = a.getCedula().compareTo(b.getCedula());
		}
		return resultado;
	}
}
