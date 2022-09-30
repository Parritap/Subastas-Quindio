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

	private ArrayList<Puja> listaPujas;
	private static Integer idAux = 0;

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

}
