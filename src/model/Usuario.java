package model;

import lombok.Data;

import java.util.ArrayList;

@Data
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




}
