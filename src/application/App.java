package application;


import controllers.AlertasController;
import controllers.FrameInicialController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.Setter;
import model.EmpresaSubasta;
import interfaces.IApplication;
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
    private HashMap<String, String> rutas = new HashMap<>();
    //Stage que carga ina alerta con un mensaje
    private Stage stageAlerta = new Stage();
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

    	//pruebas de codigo, por favor no borrarlas
    	/*
    	IAnuncio anuncio = new IAnuncio();
    	Anuncio ad = new Anuncio();
    	anuncio.crear(ad);
    	anuncio.crear(new Anuncio());
    	System.out.println(anuncio.listar().get(0));
    	System.out.println(anuncio.buscarId(1));
    	anuncio.actualizar(1, new Anuncio());
    	System.out.println(anuncio.buscarId(1));
    	System.out.println(anuncio.listar((obj)->{return obj.tiempoActivo;}, TipoOrden.ASCENDENTE));
    	Anuncio ad = new Anuncio();
    
    	System.out.println(e->{return ad.nombreAnunciante}());
    	TipoOrden ord = TipoOrden.ASCENDENTE;
    	System.out.println(ord == TipoOrden.DESCENDENTE);
    	*/
        //El singleton crea la instancia de Empresa
        empresaSubasta = ModelFactoryController.getInstance();
        //CARGO EL FRAME PRINCIPAL
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/FrameInicial.fxml"));
        Parent root = loader.load();
        FrameInicialController frameInicialController = loader.getController();
        frameInicialController.setApplication(this);
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        cargarRutas();
        stage.show();
    }

    /**
     * Metodo que inicializa las rutas en el hashMap, de esta
     * manera solo se le da el nombre de la ventana al hashMap y él devuelve la ruta
     */
    private void cargarRutas() {
        rutas.put("frame inicial", "../view/FrameInicial.fxml");
        rutas.put("crear cuenta", "../view/Login.fxml");
        rutas.put("alerta", "../view/Alertas.fxml");
        rutas.put("frameCliente", "../view/Alertas.fxml");
    }



    /**
     * METODO QUE CARGA UN STAGE CON UN MENSAJE
     * @param mensaje MENSAJE PARA MOSTRAR
     */
    public void showAlert(String mensaje) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutas.get("alerta")));
        Parent root = loader.load();
        AlertasController alertaController = loader.getController();
        alertaController.setApplication(this);
        alertaController.setLabel(mensaje);
        Scene scene = new Scene(root);
        stageAlerta.setScene(scene);
        stageAlerta.show();
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
            Parent root = loader.load();
            //IApplication es una interfaz que implementa los metodos setApplication y getApplication
            //de esta manera obligo a los controladores a implementar al menos una instancia de Application
            //para que puedan, desde ahí cerrar y abrir nuevas ventanas
            IApplication controller= loader.getController();
            controller.setApplication(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Este metodo abre una ventana y cierra todas las demás ventanas activas
     * @param frame el frame qeu se quiere mostrar
     */
    public void showStageCloseAll(String frame) {



    }
}
