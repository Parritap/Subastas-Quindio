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
import model.*;

import java.io.IOException;
import java.util.HashMap;

@Getter
@Setter

public class App extends Application {

    private static EmpresaSubasta empresaSubasta;

    private HashMap<String, String> rutas = new HashMap<>();

    private Stage stageAlerta = new Stage();

    private App application = this;

    @Override
    public void start(Stage stage) throws Exception {
        /*
    	//pruebas de codigo, por favor no borrarlas

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
        Comparar<Anuncio> compararAnuncio = (obj1, obj2)->{return obj1.getId() > obj2.getId();};
        /*for(int i=0; i<anuncio.listar().size(); i++) {
            System.out.println(anuncio.listar(compararAnuncio, TipoOrden.ASCENDENTE).get(i).getId());
        }*/

        //los que tengan el id cuyos digitos sumen mas van primero
        /*
        Comparar<Integer> comparar = (a,  b)->{return a/10+a%10<b/10+b%10;};
        for(int i=0; i<anuncio.listar().size(); i++) {
            System.out.println(anuncio.listar("id", TipoOrden.ASCENDENTE, comparar).get(i).getId());
        }
        */
        //System.out.println(anuncio.listar((obj1, obj2)->{return obj1.getId() > obj2.getId();}, TipoOrden.ASCENDENTE).get(1).getId());
        //System.out.println(anuncio.listar((obj1, obj2)->{return obj1.getId() > obj2.getId();}, TipoOrden.ASCENDENTE).get(2).getId());
        //System.out.println(anuncio.listar((obj1, obj2)->{return obj1.getId() > obj2.getId();}, TipoOrden.ASCENDENTE).get(3).getId());
    	/*System.out.println(e->{return ad.nombreAnunciante}());
    	TipoOrden ord = TipoOrden.ASCENDENTE;
    	System.out.println(ord == TipoOrden.DESCENDENTE);
        */
        empresaSubasta = ModelFactoryController.getInstance();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/FrameInicial.fxml"));
        Parent root = loader.load();
        FrameInicialController frameInicialController = loader.getController();
        frameInicialController.setApplication(this);
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * METODO QUE CARGA UN STAGE CON UN MENSAJE
     * @param mensaje MENSAJE PARA MOSTRAR
     */
    public void showAlert(String mensaje) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Alertas.fxml"));
        Parent root = loader.load();
        AlertasController alertaController = loader.getController();
        alertaController.setApplication(application);
        alertaController.setLabel(mensaje);
        Scene scene = new Scene(root);
        stageAlerta.setScene(scene);
        stageAlerta.initStyle(StageStyle.UNDECORATED);
        stageAlerta.show();
    }

    /**
     * METODO QUE CIERRA EL STAGE DE LA ALERTA
     */
    public void cerrarAlerta() {
        if(stageAlerta != null) stageAlerta.close();
    }
}
