package controllers;

import application.App;
import exceptions.EscrituraException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import interfaces.IApplication;
import model.ModelFactoryController;
import model.Usuario;

import java.io.IOException;

/**
 * ESTA CLASE PERMITE CONTROLAR EL STAGE DE CREAR CUENTA
 */
public class CrearCuentaController implements IApplication {

    //VARIABLES GLOBALES
    private App application;

    private Integer[] listaEdades;
    @FXML
    private MenuItem efectivo;

    @FXML
    private MenuItem mastercard;

    @FXML
    private MenuItem paypal;

    @FXML
    private MenuItem visa;

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

    private Integer edadSelected;

    @FXML
    private SplitMenuButton txtmetodoPago;

    /**
     * ESTE METODO PERMITE CANCELAR LA CREACION DE UNA CUENTA,
     * Y CIERRA EL STAGE
     * @param event EVENTO GENERADO AL HACER CLIC
     */
    @FXML
    void cancelarCreacion(ActionEvent event) {
        application.cerrarStage(txtName.getScene());
    }

    /**
     * ESTE METODO PERMITE CREAR UNA CUENTA CON LOS DATOS INGRESADOS
     * EN LOS TEXTFIELDS, SE DEBEN VERIFICAR QUE NO ESTEN VACIOS
     * O NULL
     * @param event GENERADO AL HACER CLIC
     */
    @FXML
    void crearCuenta(ActionEvent event) {
        //OBTENGO LOS VALORES DE LOS CAMPOS
        String name = txtName.getText();
        Integer edad = spinnerEdad.getValue();
        String cedula =txtCedula.getText();
        String correo = txtCorreo.getText();
        String password =txtContarsenia.getText();
        String formaPago =txtmetodoPago.getText();
        String direccion = txtDireccion.getText();

        if(name.equals("")  || cedula.equals("") || correo.equals("") || password.equals("")
                || direccion.equals("")){
            limpiarCamposTexto();
            application.showAlert("Verifique los campos de texto");
        }
        else if(edad <18) {
            limpiarCamposTexto();
            application.showAlert("Debe ser mayor de 18 para hacer una subasta");
        }

        else {
            Usuario usuario = new Usuario(name, edad, cedula, correo, password, direccion);
            try {
                ModelFactoryController.getInstance().crearUsuario(usuario);
                limpiarCamposTexto();
                application.showAlert("Usuario creado con éxito");
            } catch (EscrituraException e) {
                limpiarCamposTexto();
                application.showAlert(e.getMessage());
            }
        }
    }

    /**
     * ESTE METODO PERMITE VACIAR LOS CAMPOS DE TEXTOS
     * CON LA INFORMACION INTRODUCIDA
     */
    private void limpiarCamposTexto() {
        txtName.setText("");
        spinnerEdad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,18));
        txtCedula.setText("");
        txtCorreo.setText("");
        txtContarsenia.setText("");
        txtmetodoPago.setText("Metodo de Pago");
        txtDireccion.setText("");
    }

    /**
     * ESTE METODO CAMBIA EL VALOR DEL OBJETO FORMA DE PAGO,
     * CUANDO SE DE CLICK EN ALGUN VALOR, SE ACTUALIZA EL ESTADO
     * EJEMPLO, PAYPAL, MASTERCARD, VISA
     * @param event GENERADO AL HACER CLIC
     */
    @FXML
    void setValueFormaPago(ActionEvent event) {

        System.out.println(" entro " );
        if(event.getSource() == efectivo) txtmetodoPago.setText("Efectivo");
        if(event.getSource() == paypal) txtmetodoPago.setText("Paypal");
        if(event.getSource() == visa) txtmetodoPago.setText("Visa");
        if(event.getSource() == mastercard) txtmetodoPago.setText("Mastercard");
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


    @FXML
    void initialize() {
        assert efectivo != null : "fx:id=\"efectivo\" was not injected: check your FXML file 'Login.fxml'.";
        assert mastercard != null : "fx:id=\"mastercard\" was not injected: check your FXML file 'Login.fxml'.";
        assert paypal != null : "fx:id=\"paypal\" was not injected: check your FXML file 'Login.fxml'.";
        assert spinnerEdad != null : "fx:id=\"spinnerEdad\" was not injected: check your FXML file 'Login.fxml'.";
        assert txtCedula != null : "fx:id=\"txtCedula\" was not injected: check your FXML file 'Login.fxml'.";
        assert txtContarsenia != null : "fx:id=\"txtContarsenia\" was not injected: check your FXML file 'Login.fxml'.";
        assert txtCorreo != null : "fx:id=\"txtCorreo\" was not injected: check your FXML file 'Login.fxml'.";
        assert txtDireccion != null : "fx:id=\"txtDireccion\" was not injected: check your FXML file 'Login.fxml'.";
        assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'Login.fxml'.";
        assert txtmetodoPago != null : "fx:id=\"txtmetodoPago\" was not injected: check your FXML file 'Login.fxml'.";
        assert visa != null : "fx:id=\"visa\" was not injected: check your FXML file 'Login.fxml'.";
        SpinnerValueFactory<Integer> spinner =new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,18);
        spinnerEdad.setValueFactory(spinner);
    }

}
