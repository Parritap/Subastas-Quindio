package model;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import lombok.ToString;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Puja implements Serializable {
	//VARIABLES GLOBALES
	private LocalDateTime fechaDePuja;
	private Usuario usuario;
	private Double valorOfrecido;
	Integer idUsuario;

	private Anuncio anuncio;

	private boolean fueAceptada;


	/**
	 * CONSTRUCTOR
	 *
	 * @param fechaDePuja   MOMENTO EN QUE SE HIZO LA PUJA
	 * @param usuario       USUARIO QUE REALIZO LA PUJA
	 * @param valorOfrecido CANTIDAD DE DINERO OFRECIDA
	 */


	public Puja(LocalDateTime fechaDePuja, Usuario usuario, Double valorOfrecido, Anuncio anuncio) {

		this.fechaDePuja = fechaDePuja;
		this.usuario = usuario;
		this.valorOfrecido = valorOfrecido;
		this.idUsuario = usuario.getId();
		this.anuncio = anuncio;
	}

	public Puja(){
		this.fechaDePuja = LocalDateTime.now();	}

	public Puja(Integer idUsuario, Double valorOfrecido){
		this.fechaDePuja = LocalDateTime.now();
		this.idUsuario = idUsuario;
		this.valorOfrecido = valorOfrecido;
	}
}
