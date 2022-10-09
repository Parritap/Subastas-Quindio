package model;


import lombok.Getter;
import lombok.Setter;
import java.awt.Image;
import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
public class Anuncio {
	private LocalDate fecha;
	private String nombreAnunciante;
	private Image foto;
	private LocalDate fechaPublicacion;
	private LocalDate fechaTerminacion;
	private Integer valorInicial;
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
	public Anuncio(Integer id) {
			//fecha actual
			this.fechaPublicacion = LocalDate.now();
			this.tiempoActivo = 24.0;
			this.id = id;
	}
	
	//constructor completo
	public Anuncio(LocalDate fecha, String nombreAnunciante, Image foto, LocalDate fechaTerminacion,
				   Integer valorInicial, Double tiempoActivo, Boolean fueMostrado) {
		
		this.fecha = fecha;
	    this.nombreAnunciante = nombreAnunciante;
	    this.foto = foto;
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

	public boolean compararId(Integer id) {
		return this.id.compareTo(id) == 0;
	}

	public void setEstado(Estado estado){
		this.estado = estado;
	}
}