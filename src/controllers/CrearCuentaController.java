package controllers;

import application.App;
import exceptions.EscrituraException;
import exceptions.LecturaException;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.ModelFactoryController;
import model.Usuario;
import persistencia.logic.ArchivoUtil;
import utilities.Utils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class CrearCuentaController implements IApplication, Inicializable {


    private App application;
    //Nodos de la GUI
    @FXML
    private Button btnCrearCuenta;
    @FXML
    private Button btnActualizarCuenta;
    @FXML
    private Circle circleImage;
    @FXML
    private Spinner<Integer> edadSpinner;
    @FXML
    private MenuItem itemMastercard;

    @FXML
    private MenuItem itemPaypal;

    @FXML
    private Label lblCrearActualizarCuenta;

    @FXML
    private MenuItem itemVisa;

    @FXML
    private MenuItem itermEfectivo;

    @FXML
    private RadioButton rbFemale;

    @FXML
    private RadioButton rbMale;

    @FXML
    private RadioButton rbNoMore;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtTelefono;

    @FXML
    private MenuButton cmbBoxPago;

    //Variables auxiliares para la creacion de las cuentas
    //y la actualization
    private String name;

    private Integer edad;

    private String cedula;

    private String correo;

    private String direccion;

    private String telefono;

    private String contrasenia;

    private byte[] fotoPerfil;

    private String rutaFoto;
    //objeto File que referencia la foto de perfil
    private File archivoFoto;

    private CuentaController cuentaController;

    /**
     * Metodo que permite que al hacer clic se cargue una imagen
     * @param ignoredEvent generado al hacer clic
     */
    @FXML
    void cargarPerfil(MouseEvent ignoredEvent) {
        Utils.playSound(Utils.URL_CLICK_BUTTON);

        archivoFoto = Utils.seleccionarArchivo();
        rutaFoto = Utils.getRutaFotoPerfil(archivoFoto.getPath());
        fotoPerfil = Utils.obtenerBytesImagen(archivoFoto);
        if(fotoPerfil != null){
            Image img = new Image(new ByteArrayInputStream(fotoPerfil), 199, 199, false, false);
            circleImage.setFill(new ImagePattern(img));
        }

    }

    /**
     * Metodo que permite crear una cuenta
     * @param event generado al hacer clic
     */
    @FXML
    void crearCuenta(ActionEvent event) {
        Utils.playSound(Utils.URL_CLICK_BUTTON);
        //Los campos de textos requeridos para crear el usuario
        //cliente activo es el cliente que tiene la application
        if(cargarCampos() && application.getClienteActivo() == null){
            //creo el usuario con los datos obtenidos en el txt
            Usuario usuario = new Usuario(name, edad, cedula, correo, direccion, telefono, contrasenia);

            usuario.setRutaFotoPerfil(rutaFoto);
            //el singleton agrega el usuario a la lista
            try {
                ModelFactoryController.crearUsuario(usuario);
                limpiarCamposTexto();
                application.abrirAlerta("El usuario se agregó correctamente");
                application.setClienteActivo(usuario);
                //copia la foto de perfil
                ArchivoUtil.copiarArchivo(archivoFoto, new File("src"+Utils.getRutaFotoPerfil(archivoFoto.getName())));

                btnCrearCuenta.setVisible(false);
                ArchivoUtil.guardarRegistroLog("se creo el usuario "+usuario.getId()+":"+usuario.getName(), 1,
                        "Creacion de usuario", Utils.RUTA_LOG_TXT);
                btnActualizarCuenta.setVisible(true);
                cuentaController.mostrarBotonesBarraLateral();
            } catch (EscrituraException e) {
                //si el usuario ya existe entonces se lanza una excepcion
                application.abrirAlerta(e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            
        }
    }

    /**
     * Metodo que permite actualizar la cuenta al hacer clic
     * @param event generado al hacer clic
     */
    @FXML
    void actualizarCuenta(ActionEvent event) {
        Utils.playSound(Utils.URL_CLICK_BUTTON);
        cargarCampos();
        Usuario usuario = new Usuario(name, edad, cedula, correo, direccion, telefono, contrasenia);
        try {
            ModelFactoryController.actualizarUsuario(application.getClienteActivo(), usuario);
            application.abrirAlerta("El usuario se actualizó correctamente");
        } catch (LecturaException e) {
            application.abrirAlerta(e.getMessage());
        }
    }
    /**
     * Este metodo carga los campos en las variables auxiliares,
     * también verifica que los campos no estén vacíos
     * @return true si todos los campos están llenos, false si falta alguno
     */
    private boolean cargarCampos() {
        String mensaje = "";

        if(!txtName.getText().equals("")){
            name = txtName.getText();
        }else {
            mensaje += "Agregue un nombre\n";
        }

        edad = edadSpinner.getValue();

        if(!txtCedula.getText().equals("")){
            cedula = txtCedula.getText();
        }else {
            mensaje += "Agregue una cedula\n";
        }

        if(!txtCorreo.getText().equals("")){
            correo = txtCorreo.getText();
        }else{
            mensaje += "Agregue un correo \n";
        }

        if(!txtDireccion.getText().equals("")){
            direccion = txtDireccion.getText();
        }else{
            mensaje += "Agregue una direccion\n";
        }

        if(!txtTelefono.getText().equals("")){
            telefono = txtTelefono.getText();
        }else {
            mensaje += "Agregue un telefono\n";
        }

        if(!txtPassword.getText().equals("")){
            contrasenia = txtPassword.getText();
        }else {
            mensaje += "Agregue una contraseña\n";
        }

        if(fotoPerfil == null){
            mensaje += "Agregue una foto de perfil\n";
        }

        if(!mensaje.equals("")){
            application.abrirAlerta(mensaje);
            return false;
        }



        return true;
    }


    @FXML
    void initialize() {
        circleImage.setFill(new ImagePattern(new Image(Utils.profileImage)));
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(18, 100, 18);
        edadSpinner.setValueFactory(valueFactory);
        ToggleGroup group = new ToggleGroup();
        rbFemale.setToggleGroup(group);
        rbMale.setToggleGroup(group);
        rbNoMore.setToggleGroup(group);
    }

    /**
     * Este metodo permite vaciar la informacion contenida en los
     * campos de texto
     */
    private void limpiarCamposTexto() {
        txtName.setText("");
        txtPassword.setText("");
        txtCedula.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        cmbBoxPago.setText("Efectivo");
        txtTelefono.setText("");
        Image img = new Image(Utils.profileImage, 199, 199, false, false);
        circleImage.setFill(new ImagePattern(img));
        lblCrearActualizarCuenta.setText("Actualizar cuenta");
    }

    /**
     * Metodo que cambia el valor del metodo de pago
     * @param event generado al hacer clic
     */
    @FXML
    void setValueComboBox(ActionEvent event) {
        Utils.playSound(Utils.URL_CLICK_BUTTON);
        Object itemSeleccionado =  event.getSource();
        if(itemSeleccionado == itemMastercard) cmbBoxPago.setText("Mastercard");
        else if( itemSeleccionado == itemPaypal) cmbBoxPago.setText("Paypal");
        else if( itemSeleccionado == itemVisa) cmbBoxPago.setText("Visa");
        else if( itemSeleccionado == itermEfectivo) cmbBoxPago.setText("Efectivo");
    }

    @Override
    public App getApplication() {
        return application;
    }

    @Override
    public void setApplication(App application) {
        this.application =application;
    }

    @Override
    public void inicializarComponentes() {
        if(application.getClienteActivo() == null){
            btnCrearCuenta.setVisible(true);
            btnActualizarCuenta.setVisible(false);
        }else {
            btnCrearCuenta.setVisible(false);
            btnActualizarCuenta.setVisible(true);
        }
        cuentaController = application.getCuentaController();
    }

}
