package controllers;

import application.App;
import interfaces.IApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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
        //el file chooser permite abrir el explorador
        FileChooser dc = new FileChooser();
        File file = dc.showOpenDialog(new Stage());
        //obtengo el arreglo de bits de la imagen
        byte[] btImagen;
        try {
            //cargo la imagen al pane
            btImagen = Files.readAllBytes(file.toPath());
            Image img = new Image(new ByteArrayInputStream(btImagen), 199, 199, false, false);
            circleImage.setFill(new ImagePattern(img));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    }

    @FXML
    void paneMyAccount(ActionEvent ignoredEvent) {

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
        paneManejarCuenta.setVisible(false);


        borderPane.setCenter(application.obtenerPane(Utils.manageSubasta));
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
