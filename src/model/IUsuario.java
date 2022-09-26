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
	public ArrayList<Usuario> listar(String atributo, TipoOrden orden) throws CRUDExceptions {
		
	}

	@Override
	public ArrayList<Usuario> listar() throws CRUDExceptions {
		ArrayList<Usuario> resultado = new ArrayList<>();
		Iterator<Integer> iterador = listaUsuarios.keySet().iterator();
		
		while(iterador.hasNext()) {
			resultado.add(listaUsuarios.get(iterador.next()));
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

}
