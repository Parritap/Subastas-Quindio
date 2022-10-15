package application;

import controllers.AlertaController;
import exceptions.CRUDExceptions;
import interfaces.IApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import model.EmpresaSubasta;
import model.ModelFactoryController;
import model.Usuario;
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
        //El singleton crea la instancia de Empresa
        //empresaSubasta = ModelFactoryController.getInstance();
        try {
            ModelFactoryController.deserializarEmpresa();
        }
        catch(CRUDExceptions e){
            empresaSubasta = new EmpresaSubasta();
        }
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

    /**
     * Metodo que cierra la alerta con el mensaje
     */
    public void cerrarAlerta() {
        stageAlerta.close();
    }
}
