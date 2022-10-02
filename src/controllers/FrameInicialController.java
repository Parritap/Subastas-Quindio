package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import application.App;
import exceptions.EscrituraException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import interfaces.IApplication;
import lombok.Data;
import model.EmpresaSubasta;
import model.ModelFactoryController;
import persistencia.ArchivoUtil;

/**
 * CLASE QUE CONTROLA EL PRIMER FRAME
 */
@Data
public class FrameInicialController implements IApplication {


    //ATRIBUTOS DE LAS VENTANAS
    private EmpresaSubasta empresa;
    private  App application;
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

    /**
     * METODO QUE PERMITE CREAR UNA CUENTA AL HACER CLIC, DEBE ABRIR UN NUEVO STAGE CON
     * LAS OPCIONES DE CREAR CUENTA
     * @param ignoredEvent ES EL EVENTO GENERADO AL HACER CLICK EN CREAR CUENTA
     */
    @FXML
    void logIn(ActionEvent ignoredEvent) {

        //LA CLASE APP ES LA ENCARGADA DE GESTIONAR LAS VENTANAS, AL INVOCAR A SHOW STAGE
        //CARGA UNA VENTANA CON LAS OPCIONES DE CREAR CUENTA
        application.showStage("Crear cuenta");

    }



    /**
     * ESTE METODO PERMITE INICIAR SESION EN LA EMPRESA CUANDO SE DA CLICK EN
     * LA INTERFAZ
     * @param ignoredEvent EVENTO GENERADO POR EL BOTON
     * @throws IOException EL METODO GENERA UN EXCEPCION POR SI NO SE PUEDE ACCEDER A LA EMPRESA
     */
    @FXML
     void signUp(ActionEvent ignoredEvent) throws IOException {
        obtenerInstancia();
        try {
            empresa.crearUsuario(null);
        } catch (EscrituraException e) {
            application.showAlert(e.getMessage());
        }
        if (empresa.existeUsuario(null)) {
            application.showStageCloseAll("frame cliente");
        } else {
            application.showAlert("El usuario no se ha encontrado");
        }
    }

    /**
     * ESTE METODO PERMITE OBTENER LA INSTANCIA DE LA EMPRESA, DEBE EJECUTARSE ANTES
     * DE CUALQUIER STAGE. SIRVE PARA QUE EL CONTROLADOR SE COMUNIQUE CON LA EMPRESA
     */
    private void obtenerInstancia() {
        empresa = ModelFactoryController.getInstance();
    }

    /**
     * Este metodo permite detener la application, recibe el evento de nuestra propia
     * x, pero antes serializa la empresa
     * @param ignoredE evento al hacer clic en nuestra propia x
     */
    @FXML
    public void cerrarApp(ActionEvent ignoredE){
        ArchivoUtil.guardar();
        System.exit(0);
    }

    /**
     * METODO QUE PERMITE INICIALIZAR LOS OBJETOS QUE APARECEN EN LA VENTANA
     */
    @FXML
    void initialize() {
        assert checkAcept != null : "fx:id=\"checkAcept\" was not injected: check your FXML file 'FrameInicial.fxml'.";
        assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'FrameInicial.fxml'.";
        assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'FrameInicial.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'FrameInicial.fxml'.";

    }

    /**
     * METODOS IMPLEMENTADOS POR LA INTERFAZ, PERMITEN EL POLIMORFISMO A
     * LA HORA DE HACER LOS STAGES
     * @return application la instancia de application en la clase
     */
    @Override
    public App getApplication() {return application;}
    @Override
    public void setApplication(App application){
        this.application = application;
    }


}
