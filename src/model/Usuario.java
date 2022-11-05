package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import model.enums.Estado;
import model.enums.Rol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

@Setter
@Getter
@ToString
public class Usuario implements Serializable {
	//VARIABLES GLOBALES
	private String name;
	private Integer age;
	private String cedula;
	private String correo;
	private Integer cantAnuncios;
	private String direccion;
	private String password;

	private String telefono;

	private ArrayList<Puja> listaPujas;

	private Integer idListaPujas;
	//Este atributo permite conocer si el cliente actual esta activo en la app
	private Boolean activo;
	private static Integer idAux = 0;
	//Este atributo permite saber el estado del usuario, Eliminado, Nuevo, Actualizado
	private Estado estado;
	private Integer id;

	private byte[] fotoPerfil;
	private ArrayList<Anuncio> listaAnuncios;
	private Rol rol;


	//Constructores


	public Usuario(String name, Integer age, String cedula, String correo, String direccion, String telefono, String password) {
		this.name = name;
		this.age = age;
		this.cedula = cedula;
		this.correo = correo;
		this.direccion = direccion;
		this.password = password;
		this.telefono = telefono;
		this.estado = Estado.NUEVO;
		this.id = ++idAux;
		this.listaPujas = new ArrayList<>();
		this.activo=true;
		this.idListaPujas = ModelFactoryController.darIdListaPuja();
		this.cantAnuncios = 0;
		this.listaAnuncios = new ArrayList<>();
		this.rol = Rol.CLIENTE;
	}

	public Usuario(){}

	/**
	 * Metodo que permite comparar dos id, el del usuario actual y el
	 * pasado por parametro
	 * @param id a comparar con el usuario actual
	 * @return true || false
	 */
	public boolean compararId(Integer id) {return this.id.compareTo(id) == 0;}


	public boolean compararCorreo (String correo) {return this.correo.compareTo(correo) == 0;}

	/**
	 * METODO QUE PERMITE ACTUALIZAR ATRIBUTOS DE UN USUARIO SIN CAMBIAR EL
	 * ID
	 * @param nuevoUsuario EL USUARIO QUE CONTIENE LOS ATRIBUTOS A ACTUALIZAR
	 */
    public void actualizarAtributos(Usuario nuevoUsuario) {
		this.name = nuevoUsuario.getName();
		this.age = nuevoUsuario.getAge();
		this.cedula=nuevoUsuario.getCedula();
		this.correo = nuevoUsuario.getCorreo();
		this.password = nuevoUsuario.getPassword();
    }

	/**
	 * Permite comparar dos objetos
	 * @param o el objeto con el que se va a comparar
	 * @return true || false
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Usuario usuario = (Usuario) o;
		return Objects.equals(name, usuario.name) && Objects.equals(cedula, usuario.cedula);
	}

	/**
	 * Metodo que agrega un anuncio a la lista de un usuario
	 * @param anuncio el anuncio que se va a agregar
	 */
	public void addAnuncio(Anuncio anuncio) {
		boolean flag = true;
		for (Anuncio anuncioAux : this.listaAnuncios) {
			if (anuncioAux.equals(anuncio)) {
				flag = false;
				break;
			}
		}
		if (flag) listaAnuncios.add(anuncio);
		listaAnuncios.add(anuncio);
	}

	/**
	 * Verifica si un usuario tiene el rol de admin
	 * @return true || false
	 */
	public boolean isAdmin() {return this.rol == Rol.ADMINISTRADOR;}

	/**
	 *Este metodo devuelve toda la info del usuario en un string
	 * para serializarlo en un txt
	 * @return string con la info del usuario
	 */
	public String getStringUsuario() {
		StringBuilder arrobas = new StringBuilder("@@");
		//concateno todos los atributos separados por arroba
		return arrobas +
				name +
				arrobas +
				age +
				arrobas +
				cedula +
				arrobas +
				correo +
				arrobas +
				cantAnuncios +
				arrobas +
				direccion +
				arrobas +
				password +
				arrobas +
				telefono +
				arrobas +
				estado +
				arrobas +
				id +
				arrobas +
				rol;
	}
}
