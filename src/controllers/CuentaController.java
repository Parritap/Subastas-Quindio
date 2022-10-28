package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import utilities.Utils;


/**
 *Esta clase se encarga de manejar los eventos y funcionalidades de la vista de my Account
 */
public class CuentaController implements IApplication, Inicializable {

    //Instancia de application
    private App application;
    //Pane general de la vista
    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane paneListadoSubasta;
    @FXML
    private AnchorPane paneRealizarSubasta;
    private AnchorPane paneCrearCuenta;
    @FXML
    private Button btnHacerSubasta;
    @FXML
    private Button btnListadoSubasta;
    @FXML
    private Button btnCerrarSesion;

    /**
     * Cambia la ventana al panel de subasta
     * @param ignoredEvent generado al hacer clic
     */
    @FXML
    void irAlInicio(ActionEvent ignoredEvent) {
        application.loadScene(Utils.frameInicio);
    }

    /**
     * Metodo que carga el AnchorPane del listado de subastas
     * @param ignoredEvent generado al hacer clic
     */
    @FXML
    void paneListadoSubasta(ActionEvent ignoredEvent) {
        cargarPanes();
        paneCrearCuenta.setVisible(false);
        paneRealizarSubasta.setVisible(false);
        borderPane.getCenter().setTranslateY(-100);
        borderPane.getCenter().setTranslateX(-80);
        borderPane.setCenter(paneListadoSubasta);
        paneListadoSubasta.setVisible(true);
    }

    /**
     * Metodo que inicializa los panes que se cargan al hacer clic
     */
    private void cargarPanes() {
        paneCrearCuenta = application.obtenerPane(Utils.crearCuente);
        paneListadoSubasta = application.obtenerPane(Utils.listadoSubasta);
        paneRealizarSubasta = application.obtenerPane(Utils.realizarSubasta);
    }

    /**
     * Metodo que cambia el pane con la gestión de la cuenta
     * @param ignoredEvent generado al hacer clic
     */
    @FXML
    void paneMyAccount(ActionEvent ignoredEvent) {
        cargarPanes();
        borderPane.setCenter(paneCrearCuenta);
        borderPane.getCenter().setTranslateX(100);
        paneCrearCuenta.setVisible(true);
        paneRealizarSubasta.setVisible(false);
        paneListadoSubasta.setVisible(false);
    }

    /**
     * Metodo que carga el pane de realizar la subasta
     * @param event generado al hacer clic
     */
    @FXML
    void hacerSubasta(ActionEvent event) {
        cargarPanes();
        paneCrearCuenta.setVisible(false);
        paneListadoSubasta.setVisible(false);
        borderPane.setCenter(paneRealizarSubasta);
        borderPane.getCenter().setTranslateY(50);
        borderPane.getCenter().setTranslateX(100);
        paneRealizarSubasta.setVisible(true);
    }

    /**
     * Metodo que permite mostrar los botones de la barra lateral
     */
    public void mostrarBotonesBarraLateral(){
        System.out.println(" muestro botones laterales "  );
        btnListadoSubasta.setVisible(true);
        btnHacerSubasta.setVisible(true);
    }

    /**
     * Inicializa el frame cargando los posibles
     * panes que se pueden cargar
     */
    @Override
    public void inicializarComponentes() {
        application.setCuentaController(this);
        cargarPanes();
        borderPane.setCenter(paneCrearCuenta);
        borderPane.getCenter().setTranslateX(100);
        if(application.getClienteActivo() == null){
            btnHacerSubasta.setVisible(false);
            btnListadoSubasta.setVisible(false);
            btnCerrarSesion.setVisible(false);
        }else {
            btnHacerSubasta.setVisible(true);
            btnListadoSubasta.setVisible(true);
            btnCerrarSesion.setVisible(true);
        }
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



    /**
     * Metodo que cierra la sesión del usuario
     * @param event generado al hacer clic en el boton cerrar sesión.
     */
    @FXML
    void cerrarSesion(ActionEvent event) {
        application.setClienteActivo(null);
        application.loadScene(Utils.frameInicio);
    }

}
