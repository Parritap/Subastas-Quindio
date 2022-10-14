package controllers;

import application.App;
import interfaces.IApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utilities.Utils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *Esta clase se encarga de manejar los eventos y funcionalidades de la vista de cuenta
 */
public class CuentaController implements IApplication {

    //Instancia de application
    private App application;
    //Pane general de la vista
    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane paneListadoSubasta;

    @FXML
    private AnchorPane paneRealizarSubasta;
    //Contiene los metodos de pago
    @FXML
    private MenuButton cmbBoxPago;
    //imagen de la cuenta
    @FXML
    private Circle circleImage;
    //Los item de metodos de pago
    @FXML
    private MenuItem itemMastercard;
    @FXML
    private MenuItem itemPaypal;
    @FXML
    private MenuItem itemVisa;
    @FXML
    private MenuItem itermEfectivo;
    //Pane que se mostraran según las opciones
    @FXML
    private VBox paneManejarCuenta;
    //Radio buttons
    @FXML
    private RadioButton rbFemale;

    @FXML
    private RadioButton rbMale;

    @FXML
    private RadioButton rbNoMore;
    //Item para controlar la edad
    @FXML
    private Spinner<Integer> edadSpinner;
    //metodo que permite cargar una imagen en la cuenta
    @FXML
    void cargarPerfil(MouseEvent ignoredEvent) {
        Image img = new Image(new ByteArrayInputStream(Utils.obtenerBytesImagen()), 199, 199, false, false);
        circleImage.setFill(new ImagePattern(img));
    }

    /**
     * Cambia la ventana al panel de subasta
     * @param ignoredEvent generado al hacer clic
     */
    @FXML
    void irAlInicio(ActionEvent ignoredEvent) {
        application.loadScene(Utils.frameInicio);
    }

    @FXML
    void paneListadoSubasta(ActionEvent ignoredEvent) {
        cargarPanes();
        paneManejarCuenta.setVisible(false);
        paneRealizarSubasta.setVisible(false);
        borderPane.setCenter(paneListadoSubasta);
        paneListadoSubasta.setVisible(true);
    }

    private void cargarPanes() {
        paneListadoSubasta = application.obtenerPane(Utils.listadoSubasta);
        paneRealizarSubasta = application.obtenerPane(Utils.realizarSubasta);
    }

    @FXML
    void paneMyAccount(ActionEvent ignoredEvent) {
        cargarPanes();
        borderPane.setCenter(paneManejarCuenta);
        paneManejarCuenta.setVisible(true);
        paneRealizarSubasta.setVisible(false);
        paneListadoSubasta.setVisible(false);
    }
    @FXML
    void initialize() {

        circleImage.setFill(new ImagePattern(new Image(Utils.profileImage)));
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(18, 100, 18);
        edadSpinner.setValueFactory(valueFactory);
        ToggleGroup group = new ToggleGroup();
        rbFemale.setToggleGroup(group);
        rbMale.setToggleGroup(group);
        rbNoMore.setToggleGroup(group);


    }

    /**
     * Metodo que permite actualizar la cuenta al hacer clic
     * @param event generado al hacer clic
     */
    @FXML
    void actualizarCuenta(ActionEvent event) {

    }

    /**
     * Metodo que permite crear una cuenta
     * @param event generado al hacer clic
     */
    @FXML
    void crearCuenta(ActionEvent event) {

    }



    /**
     * Metodo que carga el pane de realizar la subasta
     * @param event generado al hacer clic
     */
    @FXML
    void hacerSubasta(ActionEvent event) {
        cargarPanes();
        paneManejarCuenta.setVisible(false);
        paneListadoSubasta.setVisible(false);
        borderPane.setCenter(paneRealizarSubasta);
        paneRealizarSubasta.setVisible(true);
    }







    /**
     * Metodo que cambia el valor del metodo de pago
     * @param event generado al hacer clic
     */
    @FXML
    void setValueComboBox(ActionEvent event) {
        Object itemSeleccionado =  event.getSource();
        if(itemSeleccionado == itemMastercard) cmbBoxPago.setText("Mastercard");
        else if( itemSeleccionado == itemPaypal) cmbBoxPago.setText("Paypal");
        else if( itemSeleccionado == itemVisa) cmbBoxPago.setText("Visa");
        else if( itemSeleccionado == itermEfectivo) cmbBoxPago.setText("Efectivo");
    }
    /**
     * Metodos implementados debido a la interfaz
     * @return instancia de Application
     */
    @Override
    public App getApplication() {
        return application;
    }

    /**
     * Setter de application
     * @param application instancia de application
     */
    @Override
    public void setApplication(App application) {
        this.application=application;
    }



}