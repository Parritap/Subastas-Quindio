package model;

import lombok.Data;

import java.time.LocalDate;
@Data
public class Puja {
	//VARIABLES GLOBALES
	private LocalDate fechaDePuja;
	private Usuario usuario;
	private Double valorOfrecido;

	/**
	 * CONSTRUCTOR
	 * @param fechaDePuja MOMENTO EN QUE SE HIZO LA PUJA
	 * @param usuario USUARIO QUE REALIZO LA PUJA
	 * @param valorOfrecido CANTIDAD DE DINERO OFRECIDA
	 */
	public Puja(LocalDate fechaDePuja, Usuario usuario, Double valorOfrecido) {
		
		this.fechaDePuja = fechaDePuja;
	    this.usuario = usuario;
	    this.valorOfrecido = valorOfrecido;
	}
}
