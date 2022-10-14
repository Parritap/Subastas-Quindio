package controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import application.App;
import exceptions.EscrituraException;
import exceptions.LecturaException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import interfaces.IApplication;
import lombok.Data;
import model.EmpresaSubasta;
import model.IAnuncio;
import model.ModelFactoryController;
import persistencia.ArchivoUtil;
import persistencia.ArchivoUtil;
import persistencia.Persistencia;

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
        application.showStage("crear cuenta");
    }



    /**
     * ESTE METODO PERMITE INICIAR SESION EN LA EMPRESA CUANDO SE DA CLICK EN
     * LA INTERFAZ
     * @param ignoredEvent EVENTO GENERADO POR EL BOTON
     * @throws IOException EL METODO GENERA UN EXCEPCION POR SI NO SE PUEDE ACCEDER A LA EMPRESA
     */
    @FXML
     void signUp(ActionEvent ignoredEvent) throws LecturaException {

        String name = txtName.getText();
        String passWord = txtPassword.getText();
        String correo = txtEmail.getText();
        System.out.println(name);
        System.out.println(passWord);

        if(name.equals("admin") && passWord.equals("admin") && correo.equals("admin")) {
            application.showStageCloseAll("frame admin");
            ArchivoUtil.guardarRegistroLog("Ingreso el administrador", 1, "Ingreso", ModelFactoryController.getRutaLogs("Ingreso.txt"));
        }

        else if(name.equals("admin2") && passWord.equals("admin2") && correo.equals("admin2")) {
            application.showStageCloseAll("frame admin");
            ArchivoUtil.guardarRegistroLog("ingresó el administrador secundario", 1, "Ingreso", ModelFactoryController.getRutaLogs("Ingresos.txt"));
        }

        else if(ModelFactoryController.getInstance().getIUsuario().buscarUsuario(name, passWord, correo) != null){
            application.showStageCloseAll("panel usuario");
            ArchivoUtil.guardarRegistroLog("ingresó el usuario "+name, 1, "Ingreso", ModelFactoryController.getRutaLogs("Ingresos.txt"));
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
    public void cerrarApp(ActionEvent ignoredE) throws Exception {
        ArchivoUtil.guardar();
        Persistencia.serializarEmpresa();

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
