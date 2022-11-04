package model;

import lombok.Getter;
import lombok.Setter;
import model.enums.Estado;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
public class Anuncio implements Serializable {

	//El anuncio contiene un producto
	private Producto producto;
	private String titulo;
	private byte[] imageSrc; //Es necesario cambiar la imagen a String, y contener solo la ruta para tener flexibilidad
	private LocalDateTime fechaPublicacion;
	private LocalDateTime fechaTerminacion;
	private Double valorInicial;

	private Double valorActual;
	private ArrayList<Puja> listaPujas;
	private Integer idListaPujas;

	private Double valorMinimo;
	private  Usuario usuario; //El usuario que realiza el anuncio
	/**
	 * creo la variable Estado para indicar cuando un Anuncio ha sido eliminado, actualizado
	 */
	private Estado estado;

	private boolean fueMostrado;
	/*
	cada anuncio esta relacionado a un id de tipo entero
	ya que solo vamos a manejar una lista de anuncios podemos hacerlo static
	NUEVA MANERA DE GENERAR IDS, USANDO EL SINGLE RESPONSIBILITY PRINCIPLE
	*/
	private static int idAux;
	private Integer id;

	/**
	 * CONSTRUCTOR NECESARIO PARA PRUEBAS
	 */

	public Anuncio(){}

	public Anuncio(String tituloAnuncio, byte[] bytesImg, Double valorInicialAnuncio, Long minutosDuracionAnuncio) {
		this.titulo = tituloAnuncio;
		this.imageSrc = bytesImg;
		this.valorInicial = valorInicialAnuncio;
		this.valorActual = valorInicial; //Creo que aun no se ha hecho un método para actualizar el valor actual. TRABAJAR EN ELLO.
		this.fechaPublicacion = LocalDateTime.now();
		this.fechaTerminacion = this.fechaPublicacion.plusMinutes(minutosDuracionAnuncio);
		this.idListaPujas = ModelFactoryController.darIdListaPuja();
		this.listaPujas = new ArrayList<>();
		this.fueMostrado = false;
		this.estado = Estado.NUEVO;
		this.id = ++idAux;

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

	@Override
	public String toString() {
		return "producto=" + producto.getNombre() +
				", titulo='" + titulo + '\'' +
				", fechaPublicacion=" + fechaPublicacion +
				", fechaTerminacion=" + fechaTerminacion.toString() +
				", valorInicial=" + valorInicial +
				", idListaPujas=" + idListaPujas +
				", usuario=" + usuario.getName() +
				", estado=" + estado +
				", fueMostrado=" + fueMostrado +
				", id=" + id;
	}

	public String getStringAnuncio() {
		StringBuilder arrobas = new StringBuilder("@@");
		//concateno todos los atributos separados por arroba
		return arrobas + producto.getNombre() + arrobas
				+ titulo + arrobas +
				fechaPublicacion +
				arrobas + fechaTerminacion.toString()
				+ arrobas + valorInicial + arrobas
				+ idListaPujas + arrobas + usuario.getName()
				+ arrobas + estado + arrobas + fueMostrado + arrobas + id + arrobas;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Anuncio anuncio = (Anuncio) o;
		return titulo.equals(anuncio.titulo) && Arrays.equals(imageSrc, anuncio.imageSrc) && valorInicial.equals(anuncio.valorInicial) && usuario.equals(anuncio.usuario);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(titulo, fechaPublicacion, fechaTerminacion, valorInicial, usuario);
		result = 31 * result + Arrays.hashCode(imageSrc);
		return result;
	}

}
