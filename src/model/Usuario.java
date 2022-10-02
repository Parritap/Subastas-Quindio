package model;

import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
@Data
public class Usuario implements Serializable {
	//VARIABLES GLOBALES
	private String name;
	private Integer age;
	private String cedula;
	private String correo;
	private Integer cantAnuncios;

	private String direccion;
	private String password;

	private ArrayList<Puja> listaPujas;

	//Este atributo permite conocer si el cliente actual esta activo en la app
	private boolean activo;
	private static Integer idAux = 0;
	//Este atributo permite saber el estado del usuario, Eliminado, Nuevo, Actualizado
	private Estado estado;
	private Integer id;
	public Usuario(String name, Integer age, String cedula, String correo, Integer cantAnuncios, Puja puja) {
		this.name = name;
	    this.age = age;
	    this.cedula = cedula;
	    this.correo = correo;
	    this.cantAnuncios = cantAnuncios;
		id = ++idAux;
		estado = Estado.NUEVO;
	}

	public Usuario(String name, Integer age, String cedula, String correo, String password, String direccion ) {
		this.name = name;
		this.age = age;
		this.cedula = cedula;
		this.correo = correo;
		this.direccion = direccion;
		this.password = password;
		id = ++idAux;
		estado = Estado.NUEVO;
	}

	//NO ELIMINAR ESTOS GETTERS, SI NO NO COMPILA EL CODIGO
	public boolean compararId(Integer id) {return this.id.compareTo(id) == 0;}

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
}
