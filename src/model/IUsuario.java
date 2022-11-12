package model;

import java.io.Serializable;
import java.util.ArrayList;
import exceptions.EscrituraException;
import exceptions.LecturaException;
import interfaces.CRUD;
import lombok.Data;
import model.enums.TipoOrden;

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
     * Metodo que actualiza todos los usuarios, sirve para cuando se deserializar
     * @param iUsuario objeto que contiene la lista usuarios
     */
    public void actualizarUsuarios(IUsuario iUsuario) {
        //se agregan los usuarios contenidos en iUsuario
        //se filtran y solo se agregan los que no están en la lista
        iUsuario.getListaUsuarios().forEach(usuario -> {
            if (!listaUsuarios.contains(usuario)) {
                listaUsuarios.add(usuario);
            }
        });
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

    /**
     * Método que permite buscar un usuario por su correo.
     * @param correo correo del usuario a buscar.
     * @return Usuario con el correo dado.
     * @throws LecturaException Si no se encuentra el usuario.
     */
    public Usuario buscarPorCorreo(String correo) throws LecturaException {
        for (Usuario usuarioAux : listaUsuarios) {
            if (usuarioAux.compararCorreo(correo)) {
                return usuarioAux;
            }
        }
        throw new LecturaException("No se encontró el usuario con ese correo", "usuario "+correo+" no encontrado");
    }

    /**
     * METODO QUE CREA UN USUARIO PERO ANTES VERIFICA SI EXISTE, SI EXISTE LO CREA SI NO LANZA UNA EXCEPCION
     *
     * @param usuario USUARIO QUE DESEO AGREGAR A LA LISTA USUARIOS
     * @throws EscrituraException SI EL USUARIO YA EXISTE LANZA UNA EXCEPCION
     */
    @Override
    public void crear(Usuario usuario) throws EscrituraException {
        if (usuario != null && existeUsuario(usuario)) {
            listaUsuarios.add(usuario);
            System.out.println("Se agregó un usuario" );
        } else {
            throw new EscrituraException("Ya existe un usuario con esas caracteristicas", "se intento crear un usuario ya existente ("+ (usuario != null ? usuario.getCedula() : null) +")");
        }

    }

    /**
     * METODO QUE RECIBE UN USUARIO CON ATRIBUTOS ESPECIFICOS, BUSCA EL USUARIO ALMACENADO EN
     * LA LISTA Y LE SETTEA LOS ATRIBUTOS
     *
     * @param id SE BUSCA EL USUARIO ALMACENADO
     * @param nuevoUsuario CONTIENE LOS ATRIBUTOS A SETTEAR
     * @throws LecturaException SI NO ENCUENTRA EL OBJETO LANZA UNA EXCEPCION
     */
    public void actualizar(Integer id, Usuario nuevoUsuario) throws LecturaException {
        Usuario usuarioAlmacenado = buscarId(id);
        usuarioAlmacenado.actualizarAtributos(nuevoUsuario);
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
        if (existeUsuario(obj)) {
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
        if(usuario == null) return true;
        for (Usuario aux : listaUsuarios) {
            if (aux.equals(usuario)) return false;
        }
        return true;
    }

    /**
     * Método que verífica si la contraseña pasada como argumento de un usuario coincide con la del mismo.
     * @param usuario el usuario a verificar
     * @param constrasenia la contraseña a verificar
     * @return True de ser correcta la contraseña || False de lo contrario.
     */
    public boolean verificarContrasenia (Usuario usuario, String constrasenia){
        return usuario.getPassword().equals(constrasenia);
    }

    /**
     * Este metodo retorna un string con toda la informacion de la lista de usuarios
     * @return stringUsuarios
     */
    public String getStringUsuarios() {
        StringBuilder stringUsuarios = new StringBuilder();
        for (Usuario listaUsuario : listaUsuarios) {
            stringUsuarios.append(listaUsuario.getStringUsuario());
            stringUsuarios.append("\n");
        }
        return stringUsuarios.toString();
    }

    /**
     * Metodo que permite crear un chat entre dos usuarios
     * @param emisor usuario que realiza la puja
     * @param vendedor dueño del anuncio
     */
    public void crearChat(Usuario emisor, Usuario vendedor) {
        //creo el chat del vendedor
        vendedor.crearChat(emisor, vendedor);
        //creo el chat del cliente
        emisor.crearChat(vendedor, emisor);

    }
}
