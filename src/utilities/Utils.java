package utilities;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Utils {




    /**DEVUELVE TRUE SI LA CADENA SOURCE NO ES IGUAL A NINGUNA DE LAS CADENAS
     * EN LOS DEMAS ARGUMENTOS
     * @param source CADENA QUE SE COMPARA
     * @param targets CADENAS A LAS QUE PODRIA SER IGUAL SOURCE
     * @return SI LA CADENA SOURCE NO ES IGUAL A NINGUNA DE LAS CADENAS EN TARGET
     * */
    public static Boolean isNot(String source, String[] targets){
        for(String target: targets){
            if(source.equals(target)) return false;
        }
        return true;
    }


    public static void imprimirArreglo(String[] arr){
        String resultado ="";
        for(String str: arr){
            resultado+=", "+str;
        }

        System.out.println("{"+resultado.substring(1)+"}");
    }

    //url del frame inicial donde se ubican las subastas
    public static final String frameInicio = "../view/Subastas.fxml";
    //url del frame del panel de cuenta
    public static final String frameCuenta = "../view/Cuenta.fxml";
    //Url de la imagen por defecto al crear cuenta
    public static final String profileImage = "/resources/profile.png";
    //Url del pane de los anuncios
    public static final String anuncioItem = "../view/AnuncioItem.fxml";
    //Url del pane de la gestión subasta
    public static final String realizarSubasta = "../view/GestionarSubasta.fxml";
    //Url listado subasta
    public static final String listadoSubasta = "../view/ListadoSubasta.fxml";
    //Url de la imagen del corazon de like vacío
    public static final String corazonVacio = "/resources/small_black.png";
    //Url de la imagen del corazon de like lleno
    public static final String corazonLleno = "/resources/small_filled.png";
    //Url del fxml de alerta
    public static final String frameAlerta = "../view/Alerta.fxml";
    //Variable que contiene la zona horaria
    public static final ZoneOffset ZONE_OFFSET = OffsetDateTime.now().getOffset();
    //Url del nuevo pane de crearCuenta
    public static final String crearCuente = "../view/CrearCuenta.fxml";

    /**
     * Metodo que permite abrir el FileChooser
     * y elegir una imagen que será cargada y guardada en el modelo
     * @return btImagen arreglo con los bits de la imagen
     */
    public static byte[] obtenerBytesImagen(){
        //el file chooser permite abrir el explorador
        FileChooser dc = new FileChooser();
        File file = dc.showOpenDialog(new Stage());
        //obtengo el arreglo de bits de la imagen
        byte[] btImagen;
        //cargo los bits de la imagen
        try {
            btImagen = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return btImagen;
    }



}
