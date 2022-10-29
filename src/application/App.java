package application;

import controllers.AlertaController;
import controllers.CuentaController;
import controllers.MilkGlassPane;
import exceptions.LecturaException;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import model.*;
import persistencia.logic.ArchivoUtil;
import persistencia.logic.Persistencia;
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

    //El lenguaje estará en español por defecto.
    private Language language = Language.SPANISH;
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
    private void inicializarApp(){
        empresaSubasta = ModelFactoryController.getInstance();
    }

    /**
     * Este metodo permite cambiar el scene del stage global
     * de la application
     *
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
     *
     * @param ruta donde se encuentra el pane
     * @return el pane que se encuentra en la ruta
     */
    public AnchorPane obtenerPane(String ruta) {
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
    public void abrirAlerta(String mensaje) {
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


    /**
     * Método que verífica las credenciales de un usuario y de ser correctas cambia el usuario activo.
     *
     * @param email       el email del usuario
     * @param contrasenia la contraseña del usuario
     * @throws LecturaException De haber algún error en las credenciales.
     */
    public void iniciarSesion(String email, String contrasenia) throws LecturaException {
        //"Handler" dado que handle es "lidiar con algo".
        IUsuario handler = empresaSubasta.getIUsuario();
        Usuario usuario = handler.buscarPorCorreo(email);

        if (!handler.verificarContrasenia(usuario, contrasenia)) {
            throw new LecturaException("La contraseña es incorrecta", "La contraseña pasada no es valida");
        }
        clienteActivo = usuario;
        ArchivoUtil.guardarRegistroLog("El usuario de nombre " + clienteActivo.getName()+ " y correo "+ usuario.getCorreo() + " ha iniciado sesión.", 1, "Inicio de sesión", ModelFactoryController.getRutaLogs("InicioSesion.log"));
        loadScene(Utils.frameInicio);
    }


    /**
     * Metodo que permite abrir el stage del login
     */

    public void abrirLogin() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.frameIniciarSesion));
        try {
            AnchorPane container = loader.load();
            IApplication controller = loader.getController();
            controller.setApplication(this);
            StackPane rootPane = new StackPane();
            // circle container is a child of the root pane
            rootPane.getChildren().add(container);
            // background style for the container
            container.setStyle("-fx-background-color: White");
            // create a scene with size 1280x800
            Scene scene = new Scene(rootPane, 1492, 900);
            // number of nodes that shall be spawned
            int spawnNodes = 100;
            // spawn the nodes (circles)
            for (int i = 0; i < spawnNodes; i++) {
                spawnNode(scene, container);
            }
            // create the milk glass pane
            MilkGlassPane milkGlassPane = new MilkGlassPane(container);
            milkGlassPane.setMaxSize(600, 400);
            // add the milk glass pane to the root pane
            rootPane.getChildren().add(milkGlassPane);
            container.toFront();
            stage.setScene(scene);
            stage.setFullScreen(true);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Spawns a node (circle).
     * @param scene scene
     * @param container circle container
     */
    private void spawnNode(Scene scene, Pane container) {

        // create a circle node
        Circle node = new Circle(0);

        // circle shall be ignored by parent layout
        node.setManaged(false);

        // randomly pick one of the colors
        node.setFill(Utils.colors[(int) (Math.random() * Utils.colors.length)]);

        // choose a random position
        node.setCenterX(Math.random() * scene.getWidth());
        node.setCenterY(Math.random() * scene.getHeight());

        // add the container to the circle container
        container.getChildren().add(node);

        // create a timeline that fades the circle in and and out and also moves
        // it across the screen
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(node.radiusProperty(), 0),
                        new KeyValue(node.centerXProperty(), node.getCenterX()),
                        new KeyValue(node.centerYProperty(), node.getCenterY()),
                        new KeyValue(node.opacityProperty(), 0)),
                new KeyFrame(
                        Duration.seconds(5 + Math.random() * 5),
                        new KeyValue(node.opacityProperty(), Math.random()),
                        new KeyValue(node.radiusProperty(), Math.random() * 20)),
                new KeyFrame(
                        Duration.seconds(10 + Math.random() * 20),
                        new KeyValue(node.radiusProperty(), 0),
                        new KeyValue(node.centerXProperty(), Math.random() * scene.getWidth()),
                        new KeyValue(node.centerYProperty(), Math.random() * scene.getHeight()),
                        new KeyValue(node.opacityProperty(), 0))
        );

        // timeline shall be executed once
        timeline.setCycleCount(1);

        // when we are done we spawn another node
        timeline.setOnFinished(evt -> {
            container.getChildren().remove(node);
            spawnNode(scene, container);
        });

        // finally, we play the timeline
        timeline.play();
    }

}
