package model;

import java.time.LocalDate;

public class Puja {
	LocalDate fechaDePuja;
	Usuario usuario;
	Integer valorOfrecido;
	
	public Puja(LocalDate fechaDePuja, Usuario usuario, Integer valorOfrecido) {
		
		this.fechaDePuja = fechaDePuja;
	    this.usuario = usuario;
	    this.valorOfrecido = valorOfrecido;
	}
}
