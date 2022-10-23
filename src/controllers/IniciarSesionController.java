package controllers;

import application.App;
import exceptions.LecturaException;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.ModelFactoryController;

public class IniciarSesionController implements IApplication, Inicializable {

    private App application;

    private String email;
    private String contrasenia;

    @FXML
    private Button btn_iniciarSesion;

    @FXML
    private TextField txtField_email;

    @FXML
    private TextField txt_contrasenia;

    @FXML
    void iniciarSesion(ActionEvent event) {
        try {
            if (obtenerDatos()) {
                application.iniciarSesion(email, contrasenia);
            }
        } catch (LecturaException e) {
            application.abrirAlerta("La contraseña o el email son incorrectos, por favor intente de nuevo");
        }

    }

    /**
     * Método que extrae los datos de los botones.
     */
    private boolean obtenerDatos() {
        email = txtField_email.getText();
        contrasenia = txt_contrasenia.getText();

        if (email.isEmpty() || contrasenia.isEmpty()) {
            application.abrirAlerta("Alguno de los campos está vacío");
            return false;
        }
        return true;
    }

    @Override
    public App getApplication() {
        return this.application;
    }

    @Override
    public void setApplication(App application) {
        this.application = application;
    }

    @Override
    public void inicializarComponentes() {

    }
}
