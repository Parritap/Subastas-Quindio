package model;

public class Usuario {
	String nombre;
	Integer edad;
	String cedula;
	String correo;
	Integer cantAnuncios; 
	Puja puja;
	
	public Usuario(String nombre, Integer edad, String cedula, String correo, Integer cantAnuncios, Puja puja) {
		this.nombre = nombre;
	    this.edad = edad;
	    this.cedula = cedula;
	    this.correo = correo;
	    this.cantAnuncios = cantAnuncios;
	    this.puja = puja;
	}
}
