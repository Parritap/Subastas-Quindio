package services.server;

import controllers.ServerController;
import javafx.scene.layout.VBox;
import services.client.Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Server(ServerSocket serverSocket) {
        try{
            this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch(IOException e){
            System.out.println("Error creating Server!");
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessageToClient(String messageToClient){
        try{
            bufferedWriter.write(messageToClient);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Error sending message to the Client!");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void receiveMessageFromClient(VBox vBox){
        new Thread(() -> {
            while(socket.isConnected()){
                try{
                    String messageFromClient = bufferedReader.readLine();
                    ServerController.addLabel(messageFromClient, vBox);
                }catch (IOException e){
                    e.printStackTrace();
                    System.out.println("Error receiving message from the Client!");
                    closeEverything(socket, bufferedReader, bufferedWriter);
                    break;
                }
            }
        }).start();
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        Client.cerrar(socket, bufferedReader, bufferedWriter);
    }
}
