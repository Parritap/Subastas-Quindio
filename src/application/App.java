package application;

import controllers.AlertaController;
import controllers.CuentaController;
import exceptions.CRUDExceptions;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import model.*;
import persistencia.Persistencia;
import utilities.Utils;
import java.io.IOException;
import java.util.HashMap;

@Getter
@Setter

public class App extends Application {

    //Instancia del singleton
    private static EmpresaSubasta empresaSubasta;
    //Contiene las rutas de todos los frames, la llave es el frame a mostrar
    //retorna la ruta de ese frame
    private HashMap<String, String> rutas;
    //ventana general de la application
    private Stage stage;
    private Stage stageAlerta;
    //Cliente activo es una variable que me identifica si un cliente ya ha iniciado sesión en la app
    private Usuario clienteActivo;
    private CuentaController cuentaController;
    /**
     * Main
     * @param args args
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Inicializa el programa
     *
     * @param stage ventana inicial
     * @throws Exception exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        inicializarApp();

        //CARGO EL FRAME PRINCIPAL
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.frameInicio));
        Parent root = loader.load();
        IApplication frameInicialController = loader.getController();
        frameInicialController.setApplication(this);
        Inicializable inicializableController = (Inicializable) frameInicialController;
        inicializableController.inicializarComponentes();
        Scene scene = new Scene(root);
        this.stage = stage;
        stage.setScene(scene);
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
        stage.minWidthProperty();
        stage.setOnCloseRequest(event->{
            try {
                Persistencia.serializarEmpresaUnificado();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
        stage.show();
    }


    /**
     * METODO ENCARGADO DE INICIALIZAR  LO QUE LA
     * APPLICATION NECESITE
     */
    private void inicializarApp() throws CRUDExceptions {

        empresaSubasta = ModelFactoryController.getInstance();
        Usuario usuario = new Usuario("Alejandro Arias", 20, "1209283", "alejandro@gmail.com", "cra 20 cll 12", "324334565", "1234Jose");
        Producto producto = new Producto("Popcorn", "Son de mantequilla");
        Anuncio anuncio = new Anuncio("Vendo popCorn", Utils.obtenerBytesImagen(), 300.0);
        anuncio.setProducto(producto);
        anuncio.setUsuario(usuario);
        usuario.addAnuncio(anuncio);
        empresaSubasta.addAnuncio(anuncio);
        clienteActivo = usuario;
        empresaSubasta.crearUsuario(usuario);
    }




    /**
     * Este metodo permite cambiar el scene del stage global
     * de la application
     * @param scenePath el nombre de la scene que queremos cargar
     */
    public void loadScene(String scenePath) {
        //cargo el fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            IApplication controller = loader.getController();
            controller.setApplication(this);
            Inicializable controllerInicializable = (Inicializable) controller;
            controllerInicializable.inicializarComponentes();
            this.stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que carga un FXML y devuelve ese pane
     * @param ruta donde se encuentra el pane
     * @return el pane que se encuentra en la ruta
     */
    public AnchorPane obtenerPane(String ruta){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
        try {
            AnchorPane root = loader.load();
            IApplication controller = loader.getController();
            controller.setApplication(this);
            Inicializable controllerInicializable = (Inicializable) controller;
            controllerInicializable.inicializarComponentes();
            return root;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que abre un stage con un mensaje
     * @param mensaje el mensaje que se quiere mostrar
     */
    public void abrirAlerta(String mensaje){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.frameAlerta));
        try {
            AnchorPane root = loader.load();
            IApplication controller = loader.getController();
            controller.setApplication(this);
            AlertaController controllerAux = (AlertaController) controller;
            controllerAux.setMensaje(mensaje);
            Scene scene = new Scene(root);
            stageAlerta = new Stage();
            stageAlerta.setScene(scene);
            stage.setFullScreen(false);
            stageAlerta.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCuentaController(CuentaController cuentaController){
        this.cuentaController = cuentaController;
    }

    public CuentaController getCuentaController(){
        return cuentaController;
    }
    /**
     * Metodo que cierra la alerta con el mensaje
     */
    public void cerrarAlerta() {
        stageAlerta.close();
    }


}
