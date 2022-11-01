package controllers;

import application.App;
import exceptions.LecturaException;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.media.MediaView;
import lombok.Getter;
import lombok.Setter;
import persistencia.logic.Persistencia;
import utilities.Utils;


@Setter
@Getter
public class IniciarSesionController implements IApplication, Inicializable {

    private App application;

    private String email;
    private String contrasenia;

    @FXML
    private MediaView mediaEffect;

    @FXML
    private Button btn_iniciarSesion;

    @FXML
    private TextField txtField_email;

    @FXML
    private PasswordField txt_contrasenia;

    @FXML
    void iniciarSesion(ActionEvent event) {
        try {
            if (obtenerDatos()) {
                limpiarCampos();
                application.iniciarSesion(email, contrasenia);
            }
        } catch (LecturaException e) {
            limpiarCampos();
            Persistencia.registrarExcepcion(e, "Error al iniciar sesion, credenciales incorrectas", 1);
            application.abrirAlerta("La contraseña o el email son incorrectos, por favor intente de nuevo");
        }

    }

    /**
     *Este metodo se encarga de limpia los campos de texto
     */
    void limpiarCampos() {
        txtField_email.setText("");
        txt_contrasenia.setText("");
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




    public void irLogin(ActionEvent ignoredEvent) {
        application.loadScene(Utils.frameInicio);
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
    @FXML
    public void lanzarAlerta(ActionEvent event) {
        application.abrirAlerta("Te la creiste. JAJAJ :D");
    }

}
