package model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import lombok.ToString;
import model.enums.Estado;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Puja implements Serializable {
    //VARIABLES GLOBALES
    private LocalDateTime fechaDePuja;
    private Usuario usuario;
    private Double valorOfrecido;
    private Integer idUsuario;

	private static final long serialVersionUID = 30L;


    private Anuncio anuncio;

    private boolean fueAceptada;

    private Estado estado;

	private Integer id;

	public static Integer idAux=0;
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
		this.estado = Estado.ACTIVO;
		this.id = ++idAux;
	}

	public Puja(){
	}

/*	 public Puja() {
	        this.fechaDePuja = LocalDateTime.now();
	        this.estado = Estado.ACTIVO;
	    }
*/

	public Puja(Integer idUsuario, Double valorOfrecido){
		this.fechaDePuja = LocalDateTime.now();
		this.idUsuario = idUsuario;
		this.valorOfrecido = valorOfrecido;
		this.estado = Estado.ACTIVO;
	}

	/**
	 * METODO QUE PERMITE COMPARAR IDS DADO UNO POR PARAMETRO
	 * @param id CON EL QUE SE DESEA COMPARAR
	 * @return TRUE || FALSE
	 */
	public boolean compararId(Integer id) {
		return this.id.compareTo(id) == 0;
	}

	public String getStringPuja() {
		StringBuilder arrobas = new StringBuilder("@@");
		//concateno todos los atributos separados por arroba
		return arrobas + fechaDePuja.toString()+ arrobas
				+ valorOfrecido + arrobas +
				idUsuario +
				arrobas + fueAceptada
				+ arrobas + estado + arrobas
				+ id + arrobas;
		}



    public String getCSV() {

        return (this.fechaDePuja + ","
                + this.usuario.getCorreo() + ","
                + this.valorOfrecido + ","
                + this.fueAceptada + ","
                + this.estado);
    }
}
