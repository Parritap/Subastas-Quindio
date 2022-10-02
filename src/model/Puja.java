package model;

import java.time.LocalDate;

public class Puja {
	//VARIABLES GLOBALES
	LocalDate fechaDePuja;
	Usuario usuario;
	Integer valorOfrecido;

	/**
	 * CONSTRUCTOR
	 * @param fechaDePuja MOMENTO EN QUE SE HIZO LA PUJA
	 * @param usuario USUARIO QUE REALIZO LA PUJA
	 * @param valorOfrecido CANTIDAD DE DINERO OFRECIDA
	 */
	public Puja(LocalDate fechaDePuja, Usuario usuario, Integer valorOfrecido) {
		
		this.fechaDePuja = fechaDePuja;
	    this.usuario = usuario;
	    this.valorOfrecido = valorOfrecido;
	}
}
