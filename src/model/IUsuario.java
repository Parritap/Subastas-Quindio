package model;

import java.util.ArrayList;

import exceptions.CRUDExceptions;
import exceptions.EscrituraException;
import exceptions.LecturaException;
import lombok.Data;

//los mismos metodos y atributos que IAnuncio
//pero para Usuarios
@Data
public class IUsuario implements CRUD<Usuario> {
	private ArrayList<Usuario> listaUsuarios;


	public 	IUsuario(){
		listaUsuarios = new ArrayList<>();
	}

	@Override
	public ArrayList<Usuario> listar() throws LecturaException {

		if(listaUsuarios.size() == 0){
			throw new LecturaException("No hay Anuncios para listar");
		}
		return listaUsuarios;
	}

	//METODO QUE BUSCA POR EL ID, SI NO LO ENCUENTRA LANZA UNA EXCEPCION
	@Override
	public Usuario buscarId(Integer id) throws LecturaException {
		for (Usuario usuarioAux : listaUsuarios) {
			if (usuarioAux.compararId(id)) {
				return usuarioAux;
			}
		}
		throw new LecturaException("No se encontró el usuario con ese ID");
	}

	//METODO QUE CREA UN ANUNCIO PERO ANTES VERIFICA SI EXISTE, SI EXISTE LO CREA SI NO LANZA UNA EXCEPCION
	@Override
	public void crear(Usuario usuario) throws EscrituraException {
		if(existeUsuario(usuario)) {
			usuario.setEstado(Estado.NUEVO);
			listaUsuarios.add(usuario);
		}else{
			throw new EscrituraException("Ya existe un usuario con esas caracteristicas");
		}

	}



	@Override
	public void actualizar(Integer id, Usuario nuevoUsuario) throws EscrituraException {

	}

	@Override
	public void Eliminar(Integer id) throws EscrituraException {
		boolean flag = false;
		for (Usuario usuarioAux : listaUsuarios) {
			if (usuarioAux.compararId(id)) {
				usuarioAux.setEstado(Estado.ELIMINADO);
				flag = true;
			}
		}
		if(!flag) throw new EscrituraException("No se ha podido eliminar el usuario");
	}

	/**
	 * Este método permite ordenar por un atributo y el tipo de orden,
	 * ya sea ascendente o descendete
	 *
	 * @return un ArrayList con los elementos ordenados
	 */
	@Override
	public ArrayList<Usuario> listar(String campo, TipoOrden dir) {
		ArrayList<Usuario> listaOrdenada = obtenerListaUsuarios();
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

	@Override
	public void add(Usuario obj) throws CRUDExceptions {
		if(!existeUsuario(obj)){
			listaUsuarios.add(obj);
		}
	}

	/**
	 * Este metodo me devuelve todos los elementos contenidos en el hashMap
	 */
	private ArrayList<Usuario> obtenerListaUsuarios() {
		return listaUsuarios;
	}


	public static int ordenar(String campo, Usuario a, Usuario b) {
		int resultado = 0;

		switch (campo) {
			case "edad" -> resultado = a.getAge().compareTo(b.getAge());
			case "nombre" -> resultado = a.getName().compareTo(b.getName());
			case "cedula" -> resultado = a.getCedula().compareTo(b.getCedula());
		}
		return resultado;
	}


	public boolean existeUsuario(Usuario usuario) {
		for (Usuario aux: listaUsuarios){
			if(aux.equals(usuario)) return true;
		}
		return false;
	}
}
