package application;


import controllers.AlertasController;
import controllers.FrameInicialController;
import controllers.SubastaController;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import model.EmpresaSubasta;
import interfaces.IApplication;
import model.ModelFactoryController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter

public class App extends Application {

    //Instancia del singleton
    private static EmpresaSubasta empresaSubasta;
    //Contiene las rutas de todos los frames, la llave es el frame a mostrar
    //retorna la ruta de ese frame
    private HashMap<String, String> rutas;
    //Stage que carga ina alerta con un mensaje
    private Stage stageAlerta;
    // Posiciones actuales de la ventana
    private Double x, y;
    //LISTA QUE CONTIENE LOS STAGES ACTIVOS EN LA APPLICATION
    ArrayList<Stage> listStage;

    /**
     * Main
     * @param args args
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Inicializa el programa
     * @param stage ventana inicial
     * @throws Exception exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        inicializarApp();
    	//pruebas de codigo, por favor no borrarlas
        //CARGO EL FRAME PRINCIPAL
        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutas.get("subasta")));
        Parent root = loader.load();
        SubastaController frameInicialController = loader.getController();
        frameInicialController.setApplication(this);
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        cargarRutas();
        listStage.add(stage);
        stage.show();
    }

    private void desplazarStage(Parent root, Stage stage){
        root.setOnMousePressed(evt ->{
            x=evt.getSceneX();
            y=evt.getSceneY();
        });
        root.setOnMouseDragged(evt ->{
            stage.setX(evt.getScreenX()-x);
            stage.setY(evt.getScreenY()-y);
        });
    }



    /**
     * METODO ENCARGADO DE INICIALIZAR  LO QUE LA
     * APPLICATION NECESITE
     */
    private void inicializarApp() {
        rutas = new HashMap<>();
        stageAlerta = new Stage();
        listStage = new ArrayList<>();
        cargarRutas();
        //El singleton crea la instancia de Empresa
        empresaSubasta = ModelFactoryController.getInstance();
    }


    /**
     * Metodo que inicializa las rutas en el hashMap, de esta
     * manera solo se le da el nombre de la ventana al hashMap y él devuelve la ruta
     */
    private void cargarRutas() {
        rutas.put("frame inicial", "../view/FrameInicial.fxml");
        rutas.put("crear cuenta", "../view/Login.fxml");
        rutas.put("alerta", "../view/Alertas.fxml");
        rutas.put("frameCliente", "../view/FrameCliente.fxml");
        rutas.put("frame admin", "../view/FrameAdmin.fxml");
        rutas.put("subasta", "../view/Subastas.fxml");
    }



    /**
     * METODO QUE CARGA UN STAGE CON UN MENSAJE
     * @param mensaje MENSAJE PARA MOSTRAR
     */
    public void showAlert(String mensaje){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutas.get("alerta")));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AlertasController alertaController = loader.getController();
        alertaController.setApplication(this);
        alertaController.setLabel(mensaje);
        Scene scene = new Scene(root);
        stageAlerta.setScene(scene);
        stageAlerta.show();
        fadeTransition(alertaController.getLblMensaje());
    }

    /**
     * METODO QUE REALIZA UNA TRANSITION EN UN NODO
     * @param nodo NODO AL QUE SE LE APLICA EL EFECTO
     */
    public void fadeTransition(Node nodo){
        stageAlerta.show();
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(30000), nodo);
        fadeTransition.setFromValue(1.0f);
        fadeTransition.setToValue(0.3f);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
    }
    /**
     * METODO QUE CIERRA EL STAGE DE LA ALERTA
     */
    public void cerrarAlerta() {
        if(stageAlerta != null) stageAlerta.close();
    }


    /**
     * Este metodo abre una ventana sin importar si hay más abiertas
     * @param ruta el nombre del frame que se quiere mostrar
     */
    public void showStage(String ruta) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutas.get(ruta)));
        try {
            loadFXML(loader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Este metodo abre una ventana y cierra todas las demás ventanas activas
     * @param ruta el frame qeu se quiere mostrar
     */
    public void showStageCloseAll(String ruta) {

        for (Stage stage : listStage) {
            if (stage != null) {
                stage.close();
            }
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutas.get(ruta)));
        try {
            loadFXML(loader);
        } catch (IOException ignored) {}


    }

    /**
     * ESTE METODO PERMITE CARGAR UN STAGE GENERAL, DADO UN FXML
     * CON LA RUTA
     * @param loader FXML CON LA RUTA
     * @throws IOException SI NO SE PUEDE CARGAR LANZA UNA EXCEPCION
     */
    private void loadFXML(FXMLLoader loader) throws IOException {
        //LO CARGO A UN NODO
        Parent root = loader.load();
        //OBTENGO EL CONTROLADOR
        IApplication controller = loader.getController();
        //LE HAGO SET A APPLICATION EN EL CONTROLLER
        controller.setApplication(this);
        //OBTENGO EL SCENE
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        //LO AGREGO A LA LISTA DE STAGE ACTIVOS
        listStage.add(stage);
        desplazarStage(root, stage);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public void cerrarStage(Scene scene) {
        for (Stage stage: listStage) {
            if(stage.getScene() == scene){
                System.out.println(" Entro ");
                stage.close();
            }
        }
    }
}
