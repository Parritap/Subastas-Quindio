package application;


import controllers.AlertasController;
import controllers.FrameInicialController;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import model.*;
import interfaces.IApplication;
import persistencia.ArchivoUtil;
import persistencia.ArchivoUtilLog;
import persistencia.Persistencia;

import java.io.IOException;
import java.time.LocalDate;
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
        /*
    	IAnuncio anuncio = new IAnuncio();
    	Anuncio ad = new Anuncio(25);
    	anuncio.crear(ad);
    	anuncio.crear(new Anuncio(60));
        anuncio.crear(new Anuncio(34) );
        anuncio.crear(new Anuncio(27) );
        anuncio.crear(new Anuncio(11) );
        anuncio.crear(new Anuncio(45) );
    	///System.out.println(anuncio.listar().get(0));
    	//System.out.println(anuncio.buscarId(1));
    	//anuncio.actualizar(1, new Anuncio());
    	//System.out.println(anuncio.buscarId(100000));
        /*Comparar<Anuncio> compararAnuncio = (obj1, obj2)->{return obj1.getId() > obj2.getId();};
        for(int i=0; i<anuncio.listar().size(); i++) {
            System.out.println(anuncio.listar(compararAnuncio, TipoOrden.ASCENDENTE).get(i).getId());
        }*/

        //los que tengan el id cuyos digitos sumen mas van primero
        /*
        Comparar<Integer> comparar = (a,  b)->{return a/10+a%10<b/10+b%10;};
        for(int i=0; i<anuncio.listar().size(); i++) {
            System.out.println(anuncio.listar("id", TipoOrden.ASCENDENTE, comparar).get(i).getId());
        }*/

        //System.out.println(anuncio.listar((obj1, obj2)->{return obj1.getId() > obj2.getId();}, TipoOrden.ASCENDENTE).get(1).getId());
        //System.out.println(anuncio.listar((obj1, obj2)->{return obj1.getId() > obj2.getId();}, TipoOrden.ASCENDENTE).get(2).getId());
        //System.out.println(anuncio.listar((obj1, obj2)->{return obj1.getId() > obj2.getId();}, TipoOrden.ASCENDENTE).get(3).getId());
    	//System.out.println(e->{return ad.getNombreAnunciante()}.g());
    	//TipoOrden ord = TipoOrden.ASCENDENTE;
    	//System.out.println(ord == TipoOrden.DESCENDENTE);

        //File archivoPrueba  =new File(ModelFactoryController.getRutaLogs()+"\\log1");
        //System.out.println(archivoPrueba.getAbsolutePath());
        //ArchivoUtilLog.guardarRegistroLog("mensaje de prueba", 1, "prueba", ModelFactoryController.getRutaLogs()+"\\log1");
//        IAnuncio ImplAnuncio = new IAnuncio();

  //      ImplAnuncio.crear(new Anuncio(2));
        //ImplAnuncio.buscarId(23);
        /*Usuario usr = new Usuario();
        usr.getListaPujas().add(new Puja());
        usr.getListaPujas().add(new Puja());
        usr.getListaPujas().add(new Puja());*/
        //usr.getActivo();
        //Usuario user = Persistencia.deserializarUsuario(usr, 1);
        //System.out.println(user.getCedula());
        //ArchivoUtil.guardarArchivo("C:\\td\\folder2\\prueba.txt", "prueba", true);
        //Persistencia.serializarPuja(new Puja(LocalDate.now(), new Usuario(), 122));
        //Persistencia.serializarUsuario(usr);
        //Persistencia.deserializarObj();

        //ArchivoUtil.verificarRuta("C:\\td\\persistencia\\logs\\");
        /*Usuario usr = new Usuario("dolly", 21, "1003929434", "liven@gmail.com", "aSimplePassword", "Cr1 23 Cll 19 Brr guayaquil");
        usr.getListaPujas().add(new Puja(1, 30));
        usr.getListaPujas().add(new Puja(1, 344));
        usr.getListaPujas().add(new Puja(1, 500));

        Anuncio anuncio = new Anuncio("john", 159, Estado.NUEVO, false);
        anuncio.getListaPujas().add(new Puja(1, 20));
        anuncio.getListaPujas().add(new Puja(1, 25));
        anuncio.getListaPujas().add(new Puja(1, 31));

        Usuario usr2 = new Usuario("jack", 28, "199299299", "gecko@gmail.com", "Ripper", "Cr1 144 Cll 19 Brr Burnos aires");
        usr2.getListaPujas().add(new Puja(2, 1));
        usr2.getListaPujas().add(new Puja(2, 11));
        usr2.getListaPujas().add(new Puja(2, 111));


        Anuncio anuncio2 = new Anuncio("jay", 400, Estado.NUEVO, true);
        anuncio2.getListaPujas().add(new Puja(1, 64));
        anuncio2.getListaPujas().add(new Puja(1, 128));
        anuncio2.getListaPujas().add(new Puja(1, 256));


        Usuario usr3 = new Usuario("shane", 25, "1003334333", "geid@gmail.com", "myOwnPassword", "Cr1 23 Cll 19 seoul");
        usr3.getListaPujas().add(new Puja(1, 3));
        usr3.getListaPujas().add(new Puja(1, 9));
        usr3.getListaPujas().add(new Puja(1, 27));

        Usuario usr4 = new Usuario("vieta", 9, "10029292992", "roots@gmail.com", "myOwnPassword", "Cr1 0 Cll 19 seoul");
        usr4.getListaPujas().add(new Puja(1, 4));
        usr4.getListaPujas().add(new Puja(1, 16));
        usr4.getListaPujas().add(new Puja(1, 64));

        Persistencia.serializarUsuario(usr);
        Persistencia.serializarAnuncio(anuncio);
        Persistencia.serializarUsuario(usr2);
        Persistencia.serializarUsuario(usr3);
        Persistencia.serializarUsuario(usr4);
        Persistencia.serializarAnuncio(anuncio2);
         */

        Usuario user = new Usuario();
        Persistencia.deserializarUsuario(user, "2");
        System.out.println(user.getListaPujas().get(2).getValorOfrecido());



        //CARGO EL FRAME PRINCIPAL
        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutas.get("frame inicial")));
        Parent root = loader.load();
        FrameInicialController frameInicialController = loader.getController();
        frameInicialController.setApplication(this);
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        cargarRutas();
        desplazarStage(root, stage);
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
        rutas.put("panel usuario", "../view/InterfazUsuario.fxml");
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
