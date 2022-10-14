package model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class Puja implements Serializable {
	//VARIABLES GLOBALES
	LocalDate fechaDePuja;
	Usuario usuario;
	Integer idUsuario;
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
		this.idUsuario = usuario.getId();
	}

	public Puja(){
		this.fechaDePuja = LocalDate.now();
	}

	public Puja(Integer idUsuario, Integer valorOfrecido){
		this.fechaDePuja = LocalDate.now();
		this.idUsuario = idUsuario;
		this.valorOfrecido = valorOfrecido;
	}

}
