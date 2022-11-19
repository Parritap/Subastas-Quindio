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


    /**
     * Anuncio cargado al dar click en el panel de AnuncioItem.fxml
     */
    private Anuncio anuncioSeleccionado;

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

    @FXML
    private TextField txtfValorAPujar;

    //Contiene los anuncios de la empresa en un momento dado
    private final ArrayList<Anuncio> listaAnuncios = new ArrayList<>();


    public void setAnuncioSeleccionado(Anuncio anuncio) {
        this.anuncioSeleccionado = anuncio;
    }

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
            for (Anuncio anuncio : ModelFactoryController.obtenerListaAnunciosSegunUsuario(application.getClienteActivo())) {
                if (anuncio != null && LocalDateTime.now().isBefore(anuncio.getFechaTerminacion()) && (anuncio.getEstado() != Estado.ELIMINADO)) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(this.getClass().getResource(Utils.anuncioItem));
                    AnchorPane anchorPane = fxmlLoader.load();
                    ItemController itemController = fxmlLoader.getController();
                    itemController.setSubastaController(this);
                    //si he llegado a tres columnas que salte a la siguiente fila
                    itemController.setData(anuncio);
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
     */
    public void setProductSelected() {

        this.paneInfoAnuncio.setVisible(true);

        //obtengo los atributos del anuncio por defecto
        this.lblAdName.setText(anuncioSeleccionado.getTitulo());
        this.lblAdPrice.setText("$" + anuncioSeleccionado.getValorInicial());
        this.lblFechaAnunciado.setText(formatearFecha(anuncioSeleccionado.getFechaPublicacion().toString()));
        this.lblFechaTerminacion.setText(formatearFecha(anuncioSeleccionado.getFechaTerminacion().toString()));
        this.lblValorActualProducto.setText(anuncioSeleccionado.getValorActual().toString());
        this.lblValorInicialProducto.setText(anuncioSeleccionado.getValorInicial().toString());
        this.lblNombreAnunciante.setText(anuncioSeleccionado.getUsuario().getName());
        this.lblTelAnunciante.setText(anuncioSeleccionado.getUsuario().getTelefono());

        //cargo la ruta de la imagen y la cargo en el anuncio
        System.out.println("RUTA IMAGEN: " + anuncioSeleccionado.getImagePath());
        Image image = new Image(Utils.getRutaAbsoluta()+ anuncioSeleccionado.getImagePath());

        this.adSelectedImage.setImage(image);
    }

    /**
     * Este metodo permite que al hacer clic en algún anuncio
     * se actualice el pane de la barra lateral izquierda
     *con la hora del anuncio adecuado
     * @param toString es la fecha del anuncio
     * @return la fecha formateada
     */
    private String formatearFecha(String toString) {
        //split en la fecha para obtener la hora
        String[] fecha = toString.split("T");
        //split en la hora para obtener la hora y los minutos
        return fecha[1].split("\\.")[0];
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

    /**
     * Metodo que refresca la lista de anuncios en el frame
     */
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

        if (application.getClienteActivo() != null && application.getClienteActivo().isAdmin()) {
            paneVistaAdmin.setVisible(true);
        } else {
            paneVistaAdmin.setVisible(false);
            panePujas.setLayoutX(panePujas.getLayoutX() + 300);
        }

        if (App.language == Language.SPANISH) {
            comboLanguages.setPromptText("Seleccionar idioma");
        } else {
            comboLanguages.setPromptText("Select language");
        }
    }

    /**
     * Metodo que actualiza la lista de anuncios con los
     * anuncios que se encuentran en el modelo
     */
    private void actualizarListaAnuncios() {
        listaAnuncios.clear();
        this.listaAnuncios.addAll(ModelFactoryController.getlistaAnuncios());
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
    void cambiarLenguaje(ActionEvent event) {
        App.language = Utils.stringToLanguage(comboLanguages.getValue());
        application.loadScene(Utils.frameInicio);
    }

    /**
     * Metodo que permite realizar una puja
     * al anuncio
     * @param event generado al hacer clic en hacer puja
     */
    @FXML
    private void realizarOferta(ActionEvent event) {
        //defino el valor de la puja
        double valorPuja;
        //verifico que exista un cliente activo en la app
        if (this.application.getClienteActivo() == null) {
            application.loadScene(Utils.frameInicio);
            application.abrirAlerta("Para hacer una puja primero debe iniciar sesión");
            return;
        }
        //verifico que el valor de la puja sea un número
        try {
            valorPuja = Double.parseDouble(txtfValorAPujar.getText());
        } catch (NumberFormatException e) {
            application.loadScene(Utils.frameInicio);
            application.abrirAlerta("EL valor a pujar solo puede contener números");
            return;
        }
        //verifico que el valor de la puja sea mayor a 0
        if (valorPuja < 0) {
            application.loadScene(Utils.frameInicio);
            application.abrirAlerta("No son permitidos los valores negativos");
            return;
        }
        //verifico que el valor de la puja sea mayor al valor actual de la puja
        if (valorPuja <= this.anuncioSeleccionado.getValorActual()){
            application.loadScene(Utils.frameInicio);
            application.abrirAlerta("El valor de la puja debe ser mayor al valor actual. \nValor actual: " + this.anuncioSeleccionado.getValorActual());
            return;
        }
        //si no se cumple ninguna de las condiciones anteriores
        //se procede a realizar la puja
        ModelFactoryController.hacerPuja(application.getClienteActivo(), this.anuncioSeleccionado, valorPuja);
        ModelFactoryController.crearChat(application.getClienteActivo(), this.anuncioSeleccionado.getVendedor());
        //refresco la lista de anuncios
        application.loadScene(Utils.frameInicio);
        application.abrirAlerta("Puja realizada con éxito");
    }

}


