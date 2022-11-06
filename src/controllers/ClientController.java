package controllers;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import services.client.Client;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    public AnchorPane ap_main;
    @FXML
    private Button button_send;
    @FXML
    private TextField tf_message;
    @FXML
    private VBox vbox_messages;
    @FXML
    private ScrollPane sp_main;
    private Client client;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            client = new Client(new Socket("localhost", 1234));
            System.out.println("Connected to Server");
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Error creating Client ... ");
        }

        vbox_messages.heightProperty().addListener((observable, oldValue, newValue) -> sp_main.setVvalue((Double) newValue));

        client.receiveMessageFromServer(vbox_messages);

        button_send.setOnAction(actionEvent -> {
            String messageToSend = tf_message.getText();
            if (!messageToSend.isEmpty()) {
                ServerController.addMessage(messageToSend, vbox_messages);

                client.sendMessageToServer(messageToSend);
                tf_message.clear();
            }
        });
    }

    public static void addLabel(String messageFromServer, VBox vBox){
        ServerController.addText(messageFromServer, vBox);
    }
}
