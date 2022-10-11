package model;

import java.io.Serializable;
import java.util.ArrayList;

import exceptions.ContraseniaNoValidaException;
import exceptions.CorreoNoValidoException;
import exceptions.EscrituraException;
import exceptions.LecturaException;
import interfaces.CRUD;
import lombok.Data;

/**
 * Implementación de usuarios, en esta clase solo se gestionan las acciones
 * disponibles para un usuario
 * @Author Anubis Haxard, Juan Esteban Parra, Alejandro Arias
 */

//los mismos metodos y atributos que IAnuncio
//pero para Usuarios
@Data
public class IUsuario implements CRUD<Usuario>, Serializable {

    //Variables globales
    private ArrayList<Usuario> listaUsuarios;

    public IUsuario() {
        listaUsuarios = new ArrayList<>();
    }

    /**
     * METODO QUE DEVUELVE LA LISTA DE USUARIOS
     *
     * @return LISTA DE USUARIOS
     * @throws LecturaException SI EL ARREGLO ESTA VACIO LANZA UNA EXCEPCION
     */
    @Override
    public ArrayList<Usuario> listar() throws LecturaException {
        if (listaUsuarios.size() == 0) {
            throw new LecturaException("No hay Anuncios para listar", "intento de acceso a una lista de usuarios vacia");
        }
        return listaUsuarios;
    }

    /**
     * METODO QUE BUSCA POR EL ID, SI NO LO ENCUENTRA LANZA UNA EXCEPCION
     *
     * @param id ID POR EL QUE SE BUSCA EL USUARIO
     * @return DEVUELVE EL USUARIO SI LO ENCUENTRA
     * @throws LecturaException LANZA UNA EXCEPCION SI NO LO ENCUENTRA
     */
    @Override
    public Usuario buscarId(Integer id) throws LecturaException {
        for (Usuario usuarioAux : listaUsuarios) {
            if (usuarioAux.compararId(id)) {
                return usuarioAux;
            }
        }
        throw new LecturaException("No se encontró el usuario con ese ID", "usuario "+id+" no encontrado");
    }

    public Usuario buscarUsuario(String nombre, String password, String correo) throws LecturaException {
        for(Usuario usr: listaUsuarios){
            if(usr.getName().equals(nombre)){
                if(usr.getPassword().equals(password)){
                    if(usr.getCorreo().equals(correo))
                        return usr;
                    else
                        throw new CorreoNoValidoException("correo no valido", "el usuario "+usr.getId()+" intento ingresar con un correo no valido");
                }
                else{
                    throw new ContraseniaNoValidaException("contraseña no valida", "usuario "+nombre+" intento ingresar con una contraseña no valida");
                }
            }
        }

        throw new LecturaException("usuario no encontrado", "usuario "+nombre+" no encontrado");
    }

    /**
     * METODO QUE CREA UN USUARIO PERO ANTES VERIFICA SI EXISTE, SI EXISTE LO CREA SI NO LANZA UNA EXCEPCION
     *
     * @param usuario USUARIO QUE DESEO AGREGAR A LA LISTA USUARIOS
     * @throws EscrituraException SI EL USUARIO YA EXISTE LANZA UNA EXCEPCION
     */
    @Override
    public void crear(Usuario usuario) throws EscrituraException {
        if (usuario != null && !existeUsuario(usuario)) {
            usuario.setEstado(Estado.NUEVO);
            listaUsuarios.add(usuario);
        } else {
            throw new EscrituraException("Ya existe un usuario con esas caracteristicas", "se intento crear un usuario ya existente ("+usuario.getCedula()+")");
        }

    }

    /**
     * METODO QUE RECIBE UN USUARIO CON ATRIBUTOS ESPECIFICOS, BUSCA EL USUARIO ALMACENADO EN
     * LA LISTA Y LE SETTEA LOS ATRIBUTOS
     *
     * @param id           SE BUSCA EL USUARIO ALMACENADO
     * @param nuevoUsuario CONTIENE LOS ATRIBUTOS A SETTEAR
     * @throws LecturaException SI NO ENCUENTRA EL OBJETO LANZA UNA EXCEPCION
     */

    @Override
    public void actualizar(Integer id, Usuario nuevoUsuario) throws LecturaException {
        Usuario usuarioAlmacenado = buscarId(id);
        usuarioAlmacenado.actualizarAtributos(nuevoUsuario);
    }

    /**
     * METODO QUE PERMITE ELIMINAR UN USUARIO DADO SU ID
     *
     * @param id PARAMETRO POR EL QUE SE BUSCA EL USUARIO
     * @throws EscrituraException si no encuentra un usuario con ese id lanza una excepcion
     */
    @Override
    public void Eliminar(Integer id) throws EscrituraException {
        boolean flag = false;
        for (Usuario usuarioAux : listaUsuarios) {
            if (usuarioAux.compararId(id)) {
                usuarioAux.setEstado(Estado.ELIMINADO);
                flag = true;
            }
        }
        if (!flag) throw new EscrituraException("No se ha podido eliminar el usuario ", "No se ha podido eliminar el usuario "+id);
    }

    /**
     * Este método permite ordenar por un atributo y el tipo de orden,
     * ya sea ascendente o descendente
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

    /**
     * Metodo que permite añadir un usuario a la lista
     *
     * @param obj usuario a añadir
     */
    @Override
    public void add(Usuario obj) {
        if (!existeUsuario(obj)) {
            listaUsuarios.add(obj);
        }
    }

    /**
     * Este metodo me devuelve todos los elementos contenidos en el ArrayList
     *
     * @return listaUsuarios, un ArrayList con todos los usuarios
     */
    private ArrayList<Usuario> obtenerListaUsuarios() {
        return listaUsuarios;
    }

    /**
     * Metodo que permite ordenar la lista usuarios por un atributo
     *
     * @param campo atributo pot el que se va a ordenar
     * @param a     objeto uno
     * @param b     objeto dos
     * @return retorna un int con la comparacion
     */
    public static int ordenar(String campo, Usuario a, Usuario b) {
        int resultado = 0;

        switch (campo) {
            case "edad" -> resultado = a.getAge().compareTo(b.getAge());
            case "nombre" -> resultado = a.getName().compareTo(b.getName());
            case "cedula" -> resultado = a.getCedula().compareTo(b.getCedula());
        }
        return resultado;
    }

    /**
     * Metodo que verifica si un usuario pasado por parametro existe en lista usuarios
     * @param usuario el usuario a buscar
     * @return true || false
     */
    public boolean existeUsuario(Usuario usuario) {
        if(usuario == null) return false;
        for (Usuario aux : listaUsuarios) {
            if (aux.equals(usuario)) return true;
        }
        return false;
    }
}
