package application;

import interfaces.IApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import model.Anuncio;
import model.EmpresaSubasta;
import model.ModelFactoryController;
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

    /**
     * Main
     *
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
        //pruebas de codigo, por favor no borrarlas
        //CARGO EL FRAME PRINCIPAL
        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutas.get("subasta")));
        Parent root = loader.load();
        IApplication frameInicialController = loader.getController();
        frameInicialController.setApplication(this);
        Scene scene = new Scene(root);
        this.stage = stage;
        stage.setScene(scene);
        cargarRutas();
        stage.show();
    }




    /**
     * Metodo que inicializa datos necesarios en la app
     */
    private void inicializarApp() {
        rutas = new HashMap<>();
        cargarRutas();
        //El singleton crea la instancia de Empresa
        empresaSubasta = ModelFactoryController.getInstance();

    }


    /**
     * Metodo que inicializa las rutas en el hashMap, de esta
     * manera solo se le da el nombre de la ventana al hashMap y él devuelve la ruta
     */
    private void cargarRutas() {
        //hash map con las rutas
        rutas.put("subasta", "../view/Subastas.fxml");
        rutas.put("cuenta", "../view/Cuenta.fxml");

    }


    /**
     * Este metodo permite que al hacer clic en algún anuncio
     * se actualice el pane de la barra lateral izquierda
     * @param anuncio EL ANUNCIO QUE SE VA A ACTUALIZAR
     */
    public void setProductSelected(Anuncio anuncio) {


    }

    /**
     * Este metodo permite cambiar el scene del stage global
     * de la application
     * @param scenePath el nombre de la scene que queremos cargar
     */
    public void loadScene(String scenePath) {
        //cargo el fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutas.get(scenePath)));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            IApplication controller = loader.getController();
            controller.setApplication(this);
            this.stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
