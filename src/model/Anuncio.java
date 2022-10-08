package model;

import javafx.scene.image.Image;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class Anuncio implements Serializable {

	private String name;
	private LocalDate fecha;
	private String nombreAnunciante;
	private String imageSrc; //Es necesario cambiar la imagen a String, y contener solo la ruta para tener flexibilidad
	private LocalDate fechaPublicacion;
	private LocalDate fechaTerminacion;
	private Double valorInicial;
	private ArrayList<Puja> listaPujas;
	private Double tiempoActivo;

	/**
	 * creo la variable estado para indicar cuando un Anuncio ha sido eliminado, actualizado
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
	//constructor sencillo util para hacer pruebas;
	public Anuncio() {
			//fecha actual
			this.fechaPublicacion = LocalDate.now();
			this.tiempoActivo = 24.0;
	}
	
	//constructor completo
	public Anuncio(LocalDate fecha, String nombreAnunciante, String imageSrc, LocalDate fechaTerminacion,
				   Double valorInicial, Double tiempoActivo, Boolean fueMostrado) {
		
		this.fecha = fecha;
	    this.nombreAnunciante = nombreAnunciante;
	    this.imageSrc = imageSrc;
	    //la fecha en la que se crea el objeto es la fecha del anuncio
	    this.fechaPublicacion = LocalDate.now();
	    this.fechaTerminacion = fechaTerminacion;
	    this.valorInicial = valorInicial;
	    //las pujas empiezan vacias
	    this.listaPujas = new ArrayList<>();
	    this.tiempoActivo = tiempoActivo;
	    this.fueMostrado = fueMostrado;
		//SE AGREGA EL ID
		id = ++idAux;
		//cada vez que se crea un anuncio se le pone como id el
		//valor de esta variable, la cual va aumentar cada vez
		//que creemos un anuncio
	}


	/**
	 * CONSTRUCTOR NECESARIO PARA PRUEBAS
	 */

	public Anuncio(String name, Double valorInicial, String imageSrc){
		this.name = name;
		this.valorInicial = valorInicial;
		this.imageSrc = imageSrc;
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
