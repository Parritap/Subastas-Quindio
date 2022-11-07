package services;

import application.App;
import interfaces.IApplication;
import lombok.Getter;
import lombok.Setter;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Singleton que contiene la logic del servidor
 */
@Getter
@Setter
public class Server implements Runnable, IApplication {

    private App application;
    private final String ip;
    private static Server server;
    private ServerSocket socketServer;
    private Socket socketAux;


    private Server(String ip){
        try {
            socketServer = new ServerSocket(5000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.ip = ip;
    }

    public static Server getInstance(String ip) throws IOException {
        if(server == null){
            server = new Server(ip);
        }
        return new Server(ip);
    }

    /**
     * Metodo que se ejecuta al iniciar el hilo
     * se encargara de iniciar el servidor
     * estará a la escucha de nuevos clientes que se conecten
     */
    @Override
    public void run() {
        //cero un bucle infinito para que el servidor esté a la escucha de nuevos clientes
        while (true){
            try {
                socketAux = socketServer.accept();
                DataInputStream flujo_entrada=new DataInputStream(socketAux.getInputStream());
                String mensaje =flujo_entrada.readUTF();
                System.out.println("Imprimiendo desde el servidor "+mensaje);
                socketServer.close();
            } catch (IOException ignored) {;}
        }

    }

    @Override
    public App getApplication() {
        return application;
    }

    @Override
    public void setApplication(App application) {
        this.application = application;
    }
}
