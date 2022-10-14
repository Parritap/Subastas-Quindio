package model;

import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Data
public class Anuncio implements Serializable {

	private String titulo;
	private Date fecha;
	private String nombreAnunciante;
	private byte[] imageSrc; //Es necesario cambiar la imagen a String, y contener solo la ruta para tener flexibilidad
	private Date fechaPublicacion;
	private Date fechaTerminacion;
	private Double valorInicial;
	private ArrayList<Puja> listaPujas;
	private Double tiempoActivo;

	private  Usuario usuario; //El usuario que realiza el anuncio
	/**
	 * creo la variable Estado para indicar cuando un Anuncio ha sido eliminado, actualizado
	 */
	private Estado estado;

	private Boolean fueMostrado;
	/*
	cada anuncio esta relacionado a un id de tipo entero
	ya que solo vamos a manejar una lista de anuncios podemos hacerlo static
	NUEVA MANERA DE GENERAR IDS, USANDO EL SINGLE RESPONSIBILITY PRINCIPLE
	*/
	private static int idAux;
	private Integer id;
	
	//constructor completo
	public Anuncio(String nombreAnunciante, Double valorInicial, Double tiempoActivo, Boolean fueMostrado) {
		
	    this.nombreAnunciante = nombreAnunciante;

	    //la fecha en la que se crea el objeto es la fecha del anuncio
	    this.valorInicial = valorInicial;
	    //las pujas empiezan vacias
	    this.listaPujas = new ArrayList<>();
	    this.tiempoActivo = tiempoActivo;
	    this.fueMostrado = fueMostrado;
		//SE AGREGA EL ID
		id = ++idAux;
		//cada vez que se crea un anuncio se le pone como id el
		//valor de esta variable, la cual va a aumentar cada vez
		//que creemos un anuncio
	}


	/**
	 * CONSTRUCTOR NECESARIO PARA PRUEBAS
	 */

	public Anuncio(String name, Double valorInicial){
		this.titulo = name;
		this.valorInicial = valorInicial;
	}


	/**
	 * METODO QUE PERMITE COMPARAR IDS DADO UNO POR PARAMETRO
	 * @param id CON EL QUE SE DESEA COMPARAR
	 * @return TRUE || FALSE
	 */
	public boolean compararId(Integer id) {
		return this.id.compareTo(id) == 0;
	}


}
