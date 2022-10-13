package controllers;

import application.App;
import interfaces.IApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Anuncio;
import model.ModelFactoryController;
import utilities.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Esta clase se encarga de ser el controlador del primer frame
 * que se muestra en la App
 * @author alejandroarias
 */
public class SubastaController implements IApplication{

    //Variables globales
    private App application;

    @FXML
    private Label adSelected;

    @FXML
    private ImageView adSelectedImage;

    @FXML
    private GridPane grid;

    //Contiene los anuncios de la empresa en un momento dado
    private final ArrayList<Anuncio> listaAnuncios = new ArrayList<>();


    /**
     * Este metodo carga un anuncio a la barra lateral de la app
     * (LA BARRA NARANJA)
     * @param anuncio El anuncio que se va a ubicar como seleccionado por defecto
     */

    private void loadFirstAd(Anuncio anuncio) {
        //obtengo los atributos del anuncio por defecto
        this.adSelected.setText(anuncio.getName());
        this.adSelected.setText("$" + anuncio.getValorInicial());
        //cargo la ruta de la imagen y la cargo en el anuncio
        Image image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(anuncio.getImageSrc())));
        this.adSelectedImage.setImage(image);
    }

    /**
     * Metodo que permite cargar algunos componentes necesarios antes de
     * mostrar la ventana
     */
    @FXML
    public void initialize() {
        //divido el metodo en otro para evitar acoplamiento
        inicializar();

    }

    /**
     * Contiene lo necesario para que los anuncios se puedan mostrar
     */
    private void inicializar() {
        //obtengo la lista de anuncios disponibles en la empresa
        this.listaAnuncios.addAll(ModelFactoryController.getlistaAnuncios());
        //si existe al menos un anuncio selecciono el primero como anuncio por defecto
        //para ser mostrado en la barra lateral
        if (this.listaAnuncios.size() > 0)this.loadFirstAd(this.listaAnuncios.get(0));
        //metodo que permite recorrer los anuncios y cargarlos en el pane scroll
        cargarAnuncioAlScroll();
    }

    /**
     * Metodo que recorre la lista de anuncios y los carga al pane scroll
     */
    private void cargarAnuncioAlScroll() {
        //defino la columna y la fila del gridPane
        int column = 0;
        int row = 1;

        try {
            //recorro la lista de anuncios y los convierto en un item controller
            for (Anuncio anuncio : this.listaAnuncios) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(this.getClass().getResource(Utils.anuncioItem));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(anuncio);
                //si he llegado a tres columnas que salte a la siguiente fila
                if (column == 3) {
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
        } catch (IOException var9) {
            var9.printStackTrace();
        }

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
     * @param ignoredEvent evento generado al hacer clic
     */
    @FXML
    void loadAccount(ActionEvent ignoredEvent) {
        application.loadScene(Utils.frameCuenta);
    }


}
