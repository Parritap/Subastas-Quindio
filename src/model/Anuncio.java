package model;

import java.awt.Image;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Anuncio {
	LocalDate fecha;
	 String nombreAnunciante;
	Image foto;
	LocalDate fechaPublicacion;
	LocalDate fechaTerminacion;
	Integer valorInicial;
	ArrayList<Puja> listaPujas;
	public Double tiempoActivo;
	Boolean fueMostrado;
	
	//constructor sencillo util para hacer pruebas;
		public Anuncio() {
			//fecha actual
			this.fechaPublicacion = LocalDate.now();
			this.tiempoActivo = 24.0;
		}
	
	//constructor completo
	public Anuncio(LocalDate fecha, String nombreAnunciante, Image foto, LocalDate fechaTerminacion, Integer valorInicial, Double tiempoActivo, Boolean fueMostrado) {
		
		this.fecha = fecha;
	    this.nombreAnunciante = nombreAnunciante;
	    this.foto = foto;
	    //la fecha en la que se crea el objeto es la fecha del anuncio
	    this.fechaPublicacion = LocalDate.now();
	    this.fechaTerminacion = fechaTerminacion;
	    this.valorInicial = valorInicial;
	    //las pujas empiezan vacias
	    this.listaPujas = new ArrayList<Puja>();
	    this.tiempoActivo = tiempoActivo;
	    this.fueMostrado = fueMostrado;
	}
	
	
	
}
