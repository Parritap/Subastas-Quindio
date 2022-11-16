package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import model.ModelFactoryController;
import model.Puja;

import java.time.format.DateTimeFormatter;

/**
 * Controlador usado en la vida ListaPujas.fxml que coexite con la interfaz de listado pujas, cuyo
 * controlador es SubastaItemController (cambio de nombreSugerido).
 */

@Getter
@Setter
public class SubastaPujaController implements IApplication, Inicializable {

    private ListadoSubastasController listadoSubastasController;

    private App application;

    private Puja puja;

    @FXML
    private Label lblFechaPuja;

    @FXML
    private Label lblFueAceptada;

    @FXML
    private Label lblNombreAnuncio;

    @FXML
    private Label lblValorOfrecido;


    @Override
    public App getApplication() {
        return application;
    }

    @Override
    public void setApplication(App application) {
        this.application = application;
    }


    public void setData(Puja puja) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.lblNombreAnuncio.setText(puja.getAnuncio().getTitulo());
        this.lblFechaPuja.setText(puja.getFechaDePuja().format(formatter));
        this.lblValorOfrecido.setText(puja.getValorOfrecido().toString());
        this.lblFueAceptada.setText(puja.isFueAceptada() ? "Aceptada" : "Sin aceptar");
    }

    @Override
    public void inicializarComponentes() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.lblNombreAnuncio.setText(puja.getAnuncio().getTitulo());
        this.lblFechaPuja.setText(puja.getFechaDePuja().format(formatter));
        this.lblValorOfrecido.setText(puja.getValorOfrecido().toString());
        this.lblFueAceptada.setText(puja.isFueAceptada() ? "Aceptada" : "Sin aceptar");
    }


    @FXML
    private void setPujaClicked(MouseEvent event) {
//Do nothing
    }

    @FXML
    private void MostrarOpciones(MouseEvent event) {
        //verifico si el evento fue generado por el boton derecho del mouse
        if (event.getButton().toString().equals("SECONDARY")) {
            //muestro el menu contextual
            application.mostrarMenuContextual(listadoSubastasController);
        }
    }

    /**
     * Método usado en el menú contextual (click derecho) el cual será usado para setear el estado de la
     * puja clickeada a Estado.ELIMINADO:
     * @param event
     */
    @FXML
    void eliminarPuja(ActionEvent event) {
        ModelFactoryController.eliminarPuja(puja);
        listadoSubastasController.inicializarComponentes();
    }


}
