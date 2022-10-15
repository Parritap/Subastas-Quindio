package controllers;

import application.App;
import exceptions.EscrituraException;
import interfaces.IApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.ModelFactoryController;
import model.Usuario;
import persistencia.ArchivoUtil;
import persistencia.Persistencia;
import utilities.Utils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 *Esta clase se encarga de manejar los eventos y funcionalidades de la vista de cuenta
 */
public class CuentaController implements IApplication {

    //Instancia de application
    private App application;
    //Pane general de la vista
    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtTelefono;

    @FXML
    private PasswordField txtPassword;

    //Variables auxiliares para la creacion de las cuentas
    //y la actualization
    private String name;

    private Integer edad;

    private String cedula;

    private String correo;

    private String direccion;

    private String telefono;

    private String contrasenia;

    @FXML
    private AnchorPane paneListadoSubasta;

    @FXML
    private AnchorPane paneRealizarSubasta;
    //Contiene los metodos de pago
    @FXML
    private MenuButton cmbBoxPago;
    //boton que permite crear una cuenta
    @FXML
    private Button btnCrearCuenta;
    //imagen de la cuenta
    @FXML
    private Circle circleImage;
    //Los item de metodos de pago
    @FXML
    private MenuItem itemMastercard;
    @FXML
    private MenuItem itemPaypal;
    @FXML
    private MenuItem itemVisa;
    @FXML
    private MenuItem itermEfectivo;
    //Pane que se mostraran según las opciones
    @FXML
    private VBox paneManejarCuenta;
    //Radio buttons
    @FXML
    private RadioButton rbFemale;

    @FXML
    private RadioButton rbMale;

    @FXML
    private RadioButton rbNoMore;
    //Item para controlar la edad
    @FXML
    private Spinner<Integer> edadSpinner;
    //metodo que permite cargar una imagen en la cuenta
    @FXML
    void cargarPerfil(MouseEvent ignoredEvent) {
        Image img = new Image(new ByteArrayInputStream(Utils.obtenerBytesImagen()), 199, 199, false, false);
        circleImage.setFill(new ImagePattern(img));
    }

    /**
     * Cambia la ventana al panel de subasta
     * @param ignoredEvent generado al hacer clic
     */
    @FXML
    void irAlInicio(ActionEvent ignoredEvent) {
        application.loadScene(Utils.frameInicio);
    }

    /**
     * Metodo que carga el AnchorPane del listado de subastas
     * @param ignoredEvent generado al hacer clic
     */
    @FXML
    void paneListadoSubasta(ActionEvent ignoredEvent) {
        cargarPanes();
        paneManejarCuenta.setVisible(false);
        paneRealizarSubasta.setVisible(false);
        borderPane.setCenter(paneListadoSubasta);
        paneListadoSubasta.setVisible(true);
    }

    /**
     * Metodo que inicializa los panes que se cargan al hacer clic
     */
    private void cargarPanes() {
        paneListadoSubasta = application.obtenerPane(Utils.listadoSubasta);
        paneRealizarSubasta = application.obtenerPane(Utils.realizarSubasta);
    }

    /**
     * Metodo que cambia el pane con la gestión de la cuenta
     * @param ignoredEvent generado al hacer clic
     */
    @FXML
    void paneMyAccount(ActionEvent ignoredEvent) {
        cargarPanes();
        borderPane.setCenter(paneManejarCuenta);
        paneManejarCuenta.setVisible(true);
        paneRealizarSubasta.setVisible(false);
        paneListadoSubasta.setVisible(false);
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
     * Metodo que permite actualizar la cuenta al hacer clic
     * @param event generado al hacer clic
     */
    @FXML
    void actualizarCuenta(ActionEvent event) {

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
            mensaje += "Agregue un telefono";
        }

        if(!txtPassword.getText().equals("")){
            contrasenia = txtPassword.getText();
        }else {
            mensaje += "Agregue una contraseña";
        }
        if(!mensaje.equals("")){
            application.abrirAlerta(mensaje);
            return false;
        }

        return true;
    }

    /**
     * Metodo que permite crear una cuenta
     * @param event generado al hacer clic
     */
    @FXML
    void crearCuenta(ActionEvent event) {
        if(cargarCampos() && application.getClienteActivo() == null){
            //creo el usuario con los datos obtenidos en el txt
            Usuario usuario = new Usuario(name, edad, cedula, correo, direccion, telefono, contrasenia);
            //el singleton agrega el usuario a la lista
            try {
                ModelFactoryController.addUsuario(usuario);
                limpiarCamposTexto();
                application.abrirAlerta("El usuario se agregó correctamente");
                application.setClienteActivo(usuario);
                btnCrearCuenta.setVisible(false);
                //Persistencia.serializarUsuario(usuario);
                ArchivoUtil.guardarRegistroLog("se creo el usuario "+usuario.getId()+":"+usuario.getName(), 1, "CreacionUsuario", ModelFactoryController.getRutaLogs("CreacionUsuario"));
            } catch (EscrituraException e) {
                System.out.println(" entro "  );
                //si el usuario ya existe entonces se lanza una excepcion
                application.abrirAlerta(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            //lo establezco como usuario activo

        }
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
    }


    /**
     * Metodo que carga el pane de realizar la subasta
     * @param event generado al hacer clic
     */
    @FXML
    void hacerSubasta(ActionEvent event) {
        cargarPanes();
        paneManejarCuenta.setVisible(false);
        paneListadoSubasta.setVisible(false);
        borderPane.setCenter(paneRealizarSubasta);
        paneRealizarSubasta.setVisible(true);
    }







    /**
     * Metodo que cambia el valor del metodo de pago
     * @param event generado al hacer clic
     */
    @FXML
    void setValueComboBox(ActionEvent event) {
        Object itemSeleccionado =  event.getSource();
        if(itemSeleccionado == itemMastercard) cmbBoxPago.setText("Mastercard");
        else if( itemSeleccionado == itemPaypal) cmbBoxPago.setText("Paypal");
        else if( itemSeleccionado == itemVisa) cmbBoxPago.setText("Visa");
        else if( itemSeleccionado == itermEfectivo) cmbBoxPago.setText("Efectivo");
    }
    /**
     * Metodos implementados debido a la interfaz
     * @return instancia de Application
     */
    @Override
    public App getApplication() {
        return application;
    }

    /**
     * Setter de application
     * @param application instancia de application
     */
    @Override
    public void setApplication(App application) {
        this.application=application;
    }



}
