package services;

import lombok.Getter;
import lombok.Setter;
import model.EmpresaSubasta;
import model.Mensaje;
import model.ModelFactoryController;
import persistencia.logic.ArchivoUtil;
import utilities.Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


@Setter
@Getter
public class AppServidor {
	
	private EmpresaSubasta empresaSubasta;
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

			while (true) {
				socketComunicacion = server.accept();
				Thread.sleep(100);
				// Se crea un flujo de entrada para leer los objetos que envía el cliente.
				flujoEntradaObjeto = new ObjectInputStream(socketComunicacion.getInputStream());
				recibirObjeto();
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo que permite recibir los objetos enviados desde el cliente
	 * y se recibe en nuevo hilo
	 */
	private void recibirObjeto()throws IOException, ClassNotFoundException {
		empresaSubasta = ModelFactoryController.getInstance();
		//creo un hilo al vuelo
		new Thread(() -> {
			//recibo el objeto
			Mensaje mensaje;
			try {
				mensaje = (Mensaje) flujoEntradaObjeto.readObject();
				ArchivoUtil.guardarRegistroLog(("Se recibió el mensaje: " + mensaje.getMensaje() + " del usuario: " +
						mensaje.getUsuarioEmisor().getName()), 1, "Recepción de mensaje", Utils.RUTA_LOG_TXT);
				ModelFactoryController.aniadirMensaje(mensaje);
			} catch (IOException | ClassNotFoundException e) {
				throw new RuntimeException(e);
			}

		}).start();

	}

}
