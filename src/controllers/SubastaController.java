package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Anuncio;
import model.ModelFactoryController;
import model.enums.Estado;
import model.enums.Language;
import utilities.Utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Esta clase se encarga de ser el controlador del primer frame
 * que se muestra en la App
 *
 * @author alejandroarias
 */
public class SubastaController implements IApplication, Inicializable {


    //Variables globales
    private App application;

    @FXML
    private ImageView adSelectedImage;

    @FXML
    private Button btnAccount;

    @FXML
    private Button btnFiltrar;

    @FXML
    private Button btnLogIn;

    @FXML
    private ComboBox<String> comboLanguages;

    @FXML
    private GridPane grid;

    @FXML
    private Label lbl;

    @FXML
    private Label lblAdName;

    @FXML
    private Label lblAdPrice;

    @FXML
    private Label lblFechaAnunciado;

    @FXML
    private Label lblFechaTerminacion;

    @FXML
    private Label lblNombreAnunciante;

    @FXML
    private Label lblTelAnunciante;

    @FXML
    private Label lblValorActualProducto;

    @FXML
    private Label lblValorInicialProducto;

    @FXML
    private VBox paneInfoAnuncio;

    @FXML
    private HBox panePujas;

    @FXML
    private HBox paneVistaAdmin;

    @FXML
    private TextField txtfBarraBusqueda;

    //Contiene los anuncios de la empresa en un momento dado
    private final ArrayList<Anuncio> listaAnuncios = new ArrayList<>();

    /**
     * Metodo que recorre la lista de anuncios y los carga al pane scroll
     */
    private void cargarAnuncioAlScroll() {
        actualizarListaAnuncios();
        //elimino todos los elementos del grid
        this.grid.getChildren().clear();
        //defino la columna y la fila del gridPane
        int column = 0;
        int row = 1;

        try {
            //recorro la lista de anuncios y los convierto en un item controller
            for (Anuncio anuncio : this.listaAnuncios) {
                if (anuncio !=  null && LocalDateTime.now().isBefore(anuncio.getFechaTerminacion()) && (anuncio.getEstado() != Estado.ELIMINADO)) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(this.getClass().getResource(Utils.anuncioItem));
                    AnchorPane anchorPane = fxmlLoader.load();
                    ItemController itemController = fxmlLoader.getController();
                    itemController.setSubastaController(this);
                    itemController.setData(anuncio);
                    //si he llegado a tres columnas que salte a la siguiente fila
                    if (column == 2) {
                        column = 0;
                        ++row;
                    }
                    //propiedades de grid pane para que pueda adaptarse a la pantalla
                    //es decir que sea responsive
                    this.grid.add(anchorPane, column++, row);
                    this.grid.setMinWidth(-1.0);
                    this.grid.setPrefWidth(-1.0);
                    this.grid.setMaxWidth(Double.NEGATIVE_INFINITY);
                    this.grid.setMinHeight(-1.0);
                    this.grid.setPrefHeight(-1.0);
                    this.grid.setMaxHeight(Double.NEGATIVE_INFINITY);
                    GridPane.setMargin(anchorPane, new Insets(10.0));
                }

            }
        } catch (IOException var9) {
            var9.printStackTrace();
        }

    }

    /**
     * Este metodo permite que al hacer clic en algún anuncio
     * se actualice el pane de la barra lateral izquierda
     *
     * @param anuncio EL ANUNCIO QUE SE VA A ACTUALIZAR
     */
    public void setProductSelected(Anuncio anuncio) {

        this.paneInfoAnuncio.setVisible(true);

        //obtengo los atributos del anuncio por defecto
        this.lblAdName.setText(anuncio.getTitulo());
        this.lblAdPrice.setText("$" + anuncio.getValorInicial());
        this.lblFechaAnunciado.setText(anuncio.getFechaPublicacion().toString());
        this.lblFechaTerminacion.setText(anuncio.getFechaTerminacion().toString());
        this.lblValorActualProducto.setText(anuncio.getValorActual().toString());
        this.lblValorInicialProducto.setText(anuncio.getValorInicial().toString());
        this.lblNombreAnunciante.setText(anuncio.getUsuario().getName());
        this.lblTelAnunciante.setText(anuncio.getUsuario().getTelefono());

        //cargo la ruta de la imagen y la cargo en el anuncio
        Image image = new Image(new ByteArrayInputStream(anuncio.getImageSrc()));
        this.adSelectedImage.setImage(image);
    }

    /**
     * Metodo que carga la vista de administrador de la app
     *
     * @param event generado al hacer clic
     */
    @FXML
    void paneAdministrador(MouseEvent event) {

    }

    /**
     * Metodo qeu carga la lista de pujas de un usuario
     *
     * @param ignoredEvent generado al hacer clic
     */
    @FXML
    void verPujasCompletas(MouseEvent ignoredEvent) {
        application.loadScene(Utils.frameChat);
    }

    //metodos implementados por la interfaz

    @Override
    public App getApplication() {
        return application;
    }

    @Override
    public void setApplication(App application) {
        this.application = application;
    }

    /**
     * Metodo que abre el frame de crear cuenta
     *
     * @param ignoredEvent evento generado al hacer clic
     */
    @FXML
    void loadAccount(ActionEvent ignoredEvent) {
        Utils.playSound(Utils.URL_CLICK_BUTTON);
        application.loadScene(Utils.frameCuenta);
    }


    public void actualizarAnuncios() {
        cargarAnuncioAlScroll();
    }

    @Override
    public void inicializarComponentes() {
        paneInfoAnuncio.setVisible(false);
        //obtengo la lista de anuncios disponibles en la empresa
        actualizarListaAnuncios();
        //si existe al menos un anuncio selecciono el primero como anuncio por defecto
        //para ser mostrado en la barra lateral
        //metodo que permite recorrer los anuncios y cargarlos en el pane scroll
        cargarAnuncioAlScroll();

        if (application.getClienteActivo() != null) btnLogIn.setVisible(false);
        comboLanguages.getItems().addAll(Utils.lenguajes);

        if (application.getClienteActivo() == null) {
            panePujas.setVisible(false);
            paneVistaAdmin.setVisible(false);
        }

        if(application.getClienteActivo() != null && application.getClienteActivo().isAdmin()){
            paneVistaAdmin.setVisible(true);
        }else{
            paneVistaAdmin.setVisible(false);
            panePujas.setLayoutX(panePujas.getLayoutX() + 300);
        }

        if(App.language == Language.SPANISH) {
            comboLanguages.setPromptText("Seleccionar idioma");
        }else{
            comboLanguages.setPromptText("Select language");
        }
    }

    /**
     * Metodo que actualiza la lista de anuncios con los
     * anuncios que se encuentran en el modelo
     */
    private void actualizarListaAnuncios() {
        this.listaAnuncios.addAll(ModelFactoryController.getlistaAnuncios());
        listaAnuncios.forEach(e-> System.out.println(e+"\n"));
    }

    /**
     * Metodo que detecta cuando el mouse entra al boton ir a cuenta e ir a login y los hace crecer un poco
     *
     * @param event generado al mover el mouse
     */
    @FXML
    void effectGrow(MouseEvent event) {

        if (event.getSource() == btnAccount) {
            btnAccount.setScaleX(1.1);
            btnAccount.setScaleY(1.1);
        } else if (event.getSource() == btnLogIn) {
            btnLogIn.setScaleX(1.1);
            btnLogIn.setScaleY(1.1);
        }
    }

    /**
     * Metodo que detecta cuando el mouse sale del boton ir a cuenta e ir a login y los hace volver a su tamaño original
     *
     * @param event generado al mover el mouse
     */

    @FXML
    void effectDecrement(MouseEvent event) {
        if (event.getSource() == btnAccount) {
            btnAccount.setScaleX(1);
            btnAccount.setScaleY(1);
        } else if (event.getSource() == btnLogIn) {
            btnLogIn.setScaleX(1);
            btnLogIn.setScaleY(1);
        }
    }
    /**
     * Metodo que carga la vista de login
     */
    @FXML
    void iniciarSesion(ActionEvent event) {
        //reproducir sonido
        Utils.playSound(Utils.URL_CLICK_BUTTON);
        //cargar vista de login
        application.abrirLogin();
    }

    /**
     * Metodo que filtra los anuncios por categoria
     */
    @FXML
    void filtrarAnuncios(InputMethodEvent event) {

    }

    /**
     * Metodo que permite cambiar el idioma de la app
     */
    @FXML
    void cambiarLenguaje(ActionEvent event){
        App.language  = Utils.stringToLanguage(comboLanguages.getValue());
        application.loadScene(Utils.frameInicio);

    }




}
