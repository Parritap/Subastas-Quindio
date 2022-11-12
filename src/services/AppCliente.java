package services;

import model.Mensaje;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class AppCliente {
	
	
	private String host;
	private int puerto;
	private Socket socketComunicacion;

	private ObjectOutputStream flujoSalidaObjeto;

	private ObjectInputStream flujoEntradaObjeto;
	
	public AppCliente(String host, int puerto) {
		this.puerto = puerto;
		this.host = host;
	}
	public void iniciarCliente() {

		try {
			crearConexion();
			flujoSalidaObjeto = new ObjectOutputStream(socketComunicacion.getOutputStream());
			//CIERRO EL FLUJO DE SALIDA
			/*flujoSalidaObjeto.close();
			//CIERRO EL SOCKET
			socketComunicacion.close();*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que crea la conexion con el servidor
	 * @throws IOException lanzamos la excepcion
	 */
	public void crearConexion()throws IOException {
		socketComunicacion = new Socket(host, puerto);
		System.out.println ("conectado");
	}

	/**
	 * Metodo que envía un objeto al servidor
	 * @throws IOException lanzada por el flujo de salida
	 */
	public void enviarMensaje(Mensaje mensaje)throws IOException {
		System.out.println("Se envío el cliente: " + mensaje);
		flujoSalidaObjeto.writeObject(mensaje);
	}

	/**
	 * Metodo que recibe un objeto del servidor
	 */
	private void recibirObjeto() {
		try {
			flujoEntradaObjeto = new ObjectInputStream(socketComunicacion.getInputStream());
			Mensaje mensaje = (Mensaje) flujoEntradaObjeto.readObject();
			System.out.println("Se recibió el Mensaje: " + mensaje);
			flujoEntradaObjeto.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


}
