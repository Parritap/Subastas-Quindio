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
import java.util.HashMap;

@Getter
@Setter

public class App extends Application {

    //Instancia del singleton
    private static EmpresaSubasta empresaSubasta;
    //Contiene las rutas de todos los frames, la llave es el frame a mostrar
    //retorna la ruta de ese frame
    private HashMap<String, String> rutas;



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
        stage.setScene(scene);
        cargarRutas();
        stage.show();
    }




    /**
     * METODO ENCARGADO DE INICIALIZAR  LO QUE LA
     * APPLICATION NECESITE
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
        rutas.put("subasta", "../view/Subastas.fxml");
    }


    /**
     * ESTE METODO PERMITE QUE AL CLIENTE HACER CLIC EN UN ANUNCIO SE
     * CAMBIE EL SELECCIONADO EN LA BARRA IZQUIERDA, EL EVENTO SE GENERA
     * EN LA CLASE ItemController
     * @param anuncio EL ANUNCIO QUE SE VA A ACTUALIZAR
     */
    public void setProductSelected(Anuncio anuncio) {


    }
}
