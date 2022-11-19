package utilities;

import application.App;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.ModelFactoryController;
import model.enums.Language;
import persistencia.logic.ArchivoUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

public class Utils {

    private static final String[] auxClasesConTexto = {"Button", "Label", "TextField"};
    public static final ArrayList<String> CLASES_CON_TEXTO_FX = new ArrayList<>(List.of(auxClasesConTexto));

    // Circle colors
    public static final Color[] colors = {
            new Color(0.2, 0.5, 0.8, 1.0).saturate().brighter().brighter(),
            new Color(0.3, 0.2, 0.7, 1.0).saturate().brighter().brighter(),
            new Color(0.8, 0.3, 0.9, 1.0).saturate().brighter().brighter(),
            new Color(0.4, 0.3, 0.9, 1.0).saturate().brighter().brighter(),
            new Color(0.2, 0.5, 0.7, 1.0).saturate().brighter().brighter()};


    /**
     * DEVUELVE TRUE SI LA CADENA SOURCE NO ES IGUAL A NINGUNA DE LAS CADENAS
     * EN LOS DEMOS ARGUMENTOS
     *
     * @param source  CADENA QUE SE COMPARA
     * @param targets CADENAS A LAS QUE PODRÍA SER IGUAL SOURCE
     * @return SI LA CADENA SOURCE NO ES IGUAL A NINGUNA DE LAS CADENAS EN TARGET
     */
    public static Boolean isNot(String source, String[] targets) {
        for (String target : targets) {
            if (source.equals(target)) return false;
        }
        return true;
    }

    public static String[] lenguajes = {"English", "Español"};
    //Url del sonido al hacer clic
    public static final String URL_CLICK = "src/resources/soundClic.mp3";
    //Url del sonido al hacer clic en un botón o item
    public static final String URL_CLICK_BUTTON = "src/resources/clicButtons.mp3";
    //url del frame inicial donde se ubican las subastas
    public static final String frameInicio = "../view/Subastas.fxml";
    //Url del chat de la aplicación
    public static final String frameChat = "../view/Chat.fxml";
    //url del frame menu contextual
    public static final String frameMenuContextual = "../view/menuContextual.fxml";
    //Url de los item mostrados en mis subastas
    public static final String SUBASTA_ITEM = "../view/subastaItem.fxml";

    //Url de los panel de pujas mostrado en mis ListadoSubastas.fxml
    public static final String PUJA_ITEM = "../view/PujaItem.fxml";

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
    //Ruta de la carpeta de la empresa
    public static final String RUTA_EMPRESA_SER = "src/persistencia/archivos/empresa.ser";
    //Url del nuevo pane de crearCuenta
    public static final String crearCuenta = "../view/CrearCuenta.fxml";
    //Ruta del archivo donde se guardan los registros log
    public static final String RUTA_LOG_TXT = "src/persistencia/log/excepciones.txt";
    //Ruta de la serializacion de los usuarios en txt
    public static final String RUTA_USUARIOS_TXT = "src/persistencia/archivos/Usuario.txt";
    //Ruta de la serializacion de los anuncios en txt
    public static final String RUTA_ANUNCIOS_TXT = "src/persistencia/archivos/Anuncio.txt";
    //Ruta para la generacion del CSV de los Anuncios.
    public static final String RUTA_ANUNCIOS_CSV = "src/persistencia/archivos/Anuncio.csv";
    //Ruta de la serializacion de los productos en txt
    public static final String RUTA_PRODUCTOS_TXT = "src/persistencia/archivos/Producto.txt";
    //Ruta de la serializacion de las Transacciones en txt
    public static final String RUTA_TRANSACCIONES_TXT = "src/persistencia/archivos/Transaccion.txt";
    //Ruta de los items de los chats
    public static final String CHAT_ITEM = "../view/ChatItem.fxml";
    //Ruta del logo de subastas quindio
    public static String LOGO_EMPRESA = "/resources/iconSubastaQuindio.png";
    //Ruta de las direcciones ip de los equipos
    public static final String RUTA_IP_TXT = "src/persistencia/archivos/IpConnected.txt";
    //ruta de la vista de los mensajes enviados
    public static final String MENSAJE_ENVIADO = "../view/MensajeEnviado.fxml";
    //Ruta de la vista de los mensajes recibidos
    public static final String MENSAJE_RECIBIDO = "../view/MensajeRecibido.fxml";

    //Ruta de la serializacion de las Transacciones en txt
    public static final String RUTA_EMPRESA_XML = "src/persistencia/archivos/empresa.xml";

    //Ruta de la serializacion de las pujas en txt
    public static final String RUTA_PUJAS_TXT = "src/persistencia/archivos/Puja.txt";


   /**
    * permite seleccionar un archivo del explorador
    * */
    public static File seleccionarArchivo() {
        //el file chooser permite abrir el explorador
        FileChooser dc = new FileChooser();
        File file = dc.showOpenDialog(new Stage());
        return file;
    }

    //------------------------------------------METODOS UTILS------------------------------------------
    /**
     * Metodo que permite obtener la ip del equipo
     * donde se está ejecutando la aplicación
     * @return ip del equipo
     */
    public static String getIp() {
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ArchivoUtil.serializarTxt(RUTA_IP_TXT, ip);
        return ip;
    }
    /**
     * Metodo que permite abrir el FileChooser
     * y elegir una imagen que será cargada y guardada en el modelo
     *
     * @return btImagen arreglo con los bits de la imagen
     */
    public static byte[] obtenerBytesImagen(File file) {

        //obtengo el arreglo de bits de la imagen
        byte[] btImagen = new byte[0];
        //cargo los bits de la imagen
        try {
            btImagen = Files.readAllBytes(file.toPath());
        } catch (IOException ignored) {
        }
        return btImagen;
    }

    /**
     * metodo que permite reproducir un sonido al hacer clic
     * en un boton o en un item
     *
     * @param url url del sonido
     */

    public static void playSound(String url) {
        javafx.scene.media.Media media = new javafx.scene.media.Media(new File(url).toURI().toString());
        javafx.scene.media.MediaPlayer mediaPlayer = new javafx.scene.media.MediaPlayer(media);
        mediaPlayer.play();
    }


    /**
     * Método que manipula la ruta (dada como parámetro) de un archivo FXML para crear su
     * ResourceBundle de acuerdo también a la variable pública estática App. Language
     * NOTA: Si el archivo [.properties] no existe dentro de src/persistencia/languages/[nombreArchivoFXML]
     * entonces el código dará error.
     *
     * @param rutaFXML Ruta al archivo FXML
     * @return Objeto Resource-bundle que contiene el idioma especificado en App.language
     */
    public static ResourceBundle getBundle(String rutaFXML) {
        //Particionar la ruta de donde viene el archivo fxml
        //Luego obtener el nombre del archivo fxml
        //Finalmente obtener el bundle con el nombre del archivo fxml de acuerdo al parámetro Language encontrado en la aplicación.

        String[] listasRutaArchivo = rutaFXML.split("/"); //Obtenemos la lista partition de la ruta del archivo fxml
        String aux = listasRutaArchivo[listasRutaArchivo.length - 1];//Esto retorna: "nombreArchivo.fxml"
        String paquete = aux.substring(0, aux.length() - 5);//Esto retorna "NombreArchivo";
        paquete = paquete.toLowerCase(); //Los paquetes por estándar vienen en minúscula. Esto es importante.

        //Aquí preguntamos cuál es el lenguaje de App, y dependiendo de eso retornamos uno u otro objeto Locale conteniendo el idioma y país.
        Locale l = (App.language == Language.ENGLISH ? new Locale("en", "US") : new Locale("es", "CO")); //"CO" significa "Colombia"

        //El nombre del paquete debe ser idéntico al nombre del archivo FXML sin su extensión, de otra forma, este método no sire para nada, o peor
        //Ocasionará errores en el código (Sadness and frustration...)
        return ResourceBundle.getBundle("persistencia/languages/" + paquete + "/language", l);
    }

    public static Language stringToLanguage(String str) {
        return (str.equals("English") ? Language.ENGLISH : Language.SPANISH);
    }

    public static byte[] convertirImgAByteArray (String ruta) throws IOException {

        BufferedImage bImage = ImageIO.read(new File(ruta));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jog", bos );
        return bos.toByteArray();
    }


    /**
     * Método que lee una ruta y crea un archivo de no existir, luego escribe el texto
     * pasado como parametro dentro del mismo.
     * @param ruta Archivo a escribir.
     * @param texto Texto a escribir en el archivo.
     */
    public static void escribirEnArchivo (String ruta, String texto){
        File f = new File(ruta);
        try{
            f.createNewFile();//Crea el archivo si este no existe. Si existe, simplemente no hace nada.
            Files.writeString(Path.of(ruta), String.valueOf(texto)); //Escribo en el archivo.
        }catch (IOException | NullPointerException e){
            e.printStackTrace();
        }
    }

    public static String retorarRutaConFileChooser () {
        DirectoryChooser d = new DirectoryChooser();
        d.setTitle("Elige una carpeta");
        return d.showDialog(new Stage()).toString();
    }




    public static String getRutaFotoPerfil(String nombreArchivo)
    {
    	return "/resources/FotosPerfil/"+nombreArchivo;
    }

    public static String getRutaFotoAnuncio(String nombreArchivo)
    {
    	return "/resources/FotosAnuncios/"+nombreArchivo;
    }

	public static String getRutaAbsoluta() {
		File file = new File("");
		return file.getAbsolutePath()+"\\src";
	}


}


