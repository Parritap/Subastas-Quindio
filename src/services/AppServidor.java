package services;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


@Setter
@Getter
public class AppServidor {
	

	private int puerto = 8081;
	private ServerSocket server;

	private Socket socketComunicacion;

	private ObjectInputStream flujoEntradaObjeto;


	public AppServidor() {}
	
	public void iniciarServidor() {
		try {
            // Se crea un socket servidor atendiendo a un determinado puerto.
			server = new ServerSocket(puerto);
			System.out.println("-------------------------------------------------------------");
	        System.out.println("-------------------Esperando mensajes------------------------");
			System.out.println("-------------------------------------------------------------");

	        socketComunicacion = server.accept();

			// Se crea un flujo de entrada para leer los objetos que envía el cliente.

			flujoEntradaObjeto = new ObjectInputStream(socketComunicacion.getInputStream());

			recibirObjeto();

			flujoEntradaObjeto.close();

			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que permite recibir los objetos enviados desde el cliente
	 * y se recibe en nuevo hilo
	 */
	private void recibirObjeto()throws IOException, ClassNotFoundException {
		//creo un hilo al vuelo
		new Thread(() -> {
			//recibo el objeto
			Mensaje mensaje = null;
			try {
				mensaje = (Mensaje) flujoEntradaObjeto.readObject();
			} catch (IOException | ClassNotFoundException e) {
				throw new RuntimeException(e);
			}

		}).start();


	}

}
