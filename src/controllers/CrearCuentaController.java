package controllers;

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import interfaces.IApplication;

/**
 * ESTA CLASE PERMITE CONTROLAR EL STAGE DE CREAR CUENTA
 */
public class CrearCuentaController implements IApplication {

    //VARIABLES GLOBALES
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

    /**
     * ESTE METODO PERMITE CANCELAR LA CREACION DE UNA CUENTA,
     * Y CIERRA EL STAGE
     * @param event EVENTO GENERADO AL HACER CLIC
     */
    @FXML
    void cancelarCreacion(ActionEvent event) {

    }

    /**
     * ESTE METODO PERMITE CREAR UNA CUENTA CON LOS DATOS INGRESADOS
     * EN LOS TEXTFIELDS, SE DEBEN VERIFICAR QUE NO ESTEN VACIOS
     * O NULL
     * @param event GENERADO AL HACER CLIC
     */
    @FXML
    void crearCuenta(ActionEvent event) {

    }

    /**
     * ESTE METODO CAMBIA EL VALOR DEL OBJETO FORMA DE PAGO,
     * CUANDO SE DE CLICK EN ALGUN VALOR, SE ACTUALIZA EL ESTADO
     * EJEMPLO, PAYPAL, MASTERCARD, VISA
     * @param event GENERADO AL HACER CLIC
     */
    @FXML
    void setValueFormaPago(ActionEvent event) {
        txtmetodoPago.setText(event.toString());
    }
    //METODOS GENERADOS POR LA IMPLEMENTATION DE LA INTERFAZ
    @Override
    public App getApplication() {
        return application;
    }

    @Override
    public void setApplication(App application) {
        this.application = application;
    }
}
