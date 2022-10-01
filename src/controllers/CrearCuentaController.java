package controllers;

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import interfaces.IApplication;

public class CrearCuentaController implements IApplication {

    private App application;

    @FXML
    private Spinner<Integer> spinnerEdad;

    @FXML
    private TextField txtCedula;

    @FXML
    private PasswordField txtContarsenia;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtName;

    @FXML
    private SplitMenuButton txtmetodoPago;

    @FXML
    void cancelarCreacion(ActionEvent event) {

    }

    @FXML
    void crearCuenta(ActionEvent event) {

    }

    @FXML
    void setValueFormaPago(ActionEvent event) {
        txtmetodoPago.setText(event.toString());
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
