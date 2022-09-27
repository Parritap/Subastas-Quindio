package application;

import exceptions.CRUDExceptions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Anuncio;
import model.IAnuncio;
import model.TipoOrden;

public class App extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        //Anubis, pana yo jamás lo borraria. Lindo <3
        //Att Alejito JAJAJJAJA
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/FrameInicial.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
    

}
