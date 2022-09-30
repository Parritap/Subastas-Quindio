package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import exceptions.EscrituraException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import model.EmpresaSubasta;
import model.ModelFactoryController;
import model.Usuario;

public class FrameInicialController {

    private EmpresaSubasta empresa;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox checkAcept;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    void logIn(ActionEvent event) {
        System.out.println(" log in "  );
    }

    @FXML
    void signUp(ActionEvent event) {
        obtenerInstancia();
        Usuario usuario = new Usuario("Alejandro", 20, "10032", "alejo1@", 0, null);
        usuario.setName(txtName.getText());
        usuario.setCorreo(txtEmail.getText());
        usuario.setPassword(txtPassword.getText());
        usuario.setId(-1);
        try {
            empresa.crearUsuario(usuario);
        } catch (EscrituraException e) {
            System.out.println("e = " + e);
        }
        if(empresa.existeUsuario(usuario)){
            cargarStageOnNewWindow("FrameCliente");
        }else{

        }
    }

    private void cargarStageOnNewWindow(String frameCliente) {
    }

    private void obtenerInstancia() {
        empresa = ModelFactoryController.getInstance();
    }

    @FXML
    void initialize() {
        assert checkAcept != null : "fx:id=\"checkAcept\" was not injected: check your FXML file 'FrameInicial.fxml'.";
        assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'FrameInicial.fxml'.";
        assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'FrameInicial.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'FrameInicial.fxml'.";

    }

}
