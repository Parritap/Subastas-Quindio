package model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Usuario {
	private String name;
	private Integer age;
	private String cedula;
	private String correo;
	private Integer cantAnuncios;

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
	}

	//NO ELIMINAR ESTOS GETTERS, SI NO NO COMPILA EL CODIGO
	public Integer getAge(){
		return age;
	}

	public String  getName(){
		return name;
	}

	public String getCedula(){
		return cedula;
	}

	public void setEstado(Estado nuevo) {
		estado = nuevo;
	}

	public boolean compararId(Integer id) {return this.id.compareTo(id) == 0;}
}
