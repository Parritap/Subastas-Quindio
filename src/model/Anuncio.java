package model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.Image;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
public class Anuncio implements Serializable {

	private Integer idFoto;
	//El anuncio contiene un producto
	private Producto producto;
	private String titulo;
	private Date fecha;
	private String nombreAnunciante;
	private byte[] imageSrc; //Es necesario cambiar la imagen a String, y contener solo la ruta para tener flexibilidad
	private LocalDate fechaPublicacion;
	private LocalDate fechaTerminacion;
	private Double valorInicial;
	private ArrayList<Puja> listaPujas;
	private Integer idListaPujas;
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


	public Anuncio(Double tiempoActivo){
		//fecha actual
		this.fechaPublicacion = LocalDate.now();
		this.tiempoActivo = tiempoActivo;
		this.id = ++idAux;
		this.idFoto = idAux;
		this.idListaPujas = ModelFactoryController.darIdListaPuja();
		this.listaPujas = new ArrayList<>();
	}

	public Anuncio(Integer id) {
		//fecha actual
		this.fechaPublicacion = LocalDate.now();
		this.tiempoActivo = 24.0;
		this.id = id;
		this.idFoto = idAux;
		this.idListaPujas = ModelFactoryController.darIdListaPuja();
		this.listaPujas = new ArrayList<>();
	}

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

	public Anuncio(){}
	
	//constructor completo

	public Anuncio(String name, Double valorInicial){
		this.titulo = name;
		this.valorInicial = valorInicial;
	}

	public Anuncio(String tituloAnuncio, byte[] bytesImg, Double valorInicialAnuncio) {
		this.titulo = tituloAnuncio;
		this.imageSrc = bytesImg;
		this.valorInicial = valorInicialAnuncio;
	}


	/**
	 * METODO QUE PERMITE COMPARAR IDS DADO UNO POR PARAMETRO
	 * @param id CON EL QUE SE DESEA COMPARAR
	 * @return TRUE || FALSE
	 */
	public boolean compararId(Integer id) {
		return this.id.compareTo(id) == 0;
	}

	/**
	 * metodo que devuelve el nombre del producto asociado con este anuncio
	 * @return el nombre del producto
	 */
	public String getNameProducto() {
		return producto.getNombre();
	}

	/**
	 * Este metodo devuelve la cantidad de pujas que se ha hecho en ese anuncio
	 * @return el total de pujas en el anuncio
	 */
	public String getTotalPujas() {
		return listaPujas.size()+"";
	}

	/**
	 * Este metodo devuelve el valor de la puja más alta
	 * @return el string del valor de la puja mas alta
	 */
	public String getValorMasAlto() {
		//El valor de la puja más alta será 0
		Double masAlto = 0.0;
		for (Puja aux : listaPujas) {
			//si el valor de la puja es mayor entonces cambio el valor de la puja más alta
			if (aux.getValorOfrecido().compareTo(masAlto) == 0) {
				masAlto = aux.getValorOfrecido();
			}
		}
		return masAlto+"";
	}
}
