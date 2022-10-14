package model;

import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Data
public class Anuncio implements Serializable {

	//El anuncio contiene un producto
	private Producto producto;
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
