package utilities;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Utils {


    // Circle colors
    public  static final Color[] colors = {
            new Color(0.2,0.5,0.8, 1.0).saturate().brighter().brighter(),
            new Color(0.3,0.2,0.7,1.0).saturate().brighter().brighter(),
            new Color(0.8,0.3,0.9,1.0).saturate().brighter().brighter(),
            new Color(0.4,0.3,0.9,1.0).saturate().brighter().brighter(),
            new Color(0.2,0.5,0.7,1.0).saturate().brighter().brighter()};

    /**DEVUELVE TRUE SI LA CADENA SOURCE NO ES IGUAL A NINGUNA DE LAS CADENAS
     * EN LOS DEMOS ARGUMENTOS
     * @param source CADENA QUE SE COMPARA
     * @param targets CADENAS A LAS QUE PODRÍA SER IGUAL SOURCE
     * @return SI LA CADENA SOURCE NO ES IGUAL A NINGUNA DE LAS CADENAS EN TARGET
     * */
    public static Boolean isNot(String source, String[] targets){
        for(String target: targets){
            if(source.equals(target)) return false;
        }
        return true;
    }
    public static String[] lenguajes = {"English", "Español"};
    //Url del sonido al hacer clic
    public static final String URL_CLICK = "src/resources/soundClic.mp3";
    //url del frame inicial donde se ubican las subastas
    public static final String frameInicio = "../view/Subastas.fxml";
    //Url de los item mostrados en mis subastas
    public static final String SUBASTA_ITEM = "../view/subastaItem.fxml";
    //url del frame del panel de cuenta
    public static final String frameCuenta = "../view/Cuenta.fxml";

    public static final String frameIniciarSesion = "../view/IniciarSesionCampos.fxml";
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

    /**
     * metodo que permite reproducir un sonido al hacer clic
     * en un boton o en un item
     * @param url url del sonido
     *
     */

    public static void playSound(String url){
        javafx.scene.media.Media media = new javafx.scene.media.Media(new File(url).toURI().toString());
        javafx.scene.media.MediaPlayer mediaPlayer = new javafx.scene.media.MediaPlayer(media);
        mediaPlayer.play();
    }


    public static Paint rgbToColor(int colorAleatorio, int colorAleatorio2, int colorAleatorio3) {
        return javafx.scene.paint.Color.rgb(colorAleatorio, colorAleatorio2, colorAleatorio3);
    }
}
