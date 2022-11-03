package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.Setter;
import model.Anuncio;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class SubastaItemController implements IApplication, Inicializable {

    private App application;

    @FXML
    private Label lblFechaPublicado;

    @FXML
    private Label lblNomProducto;

    @FXML
    private Label lblTotalPujas;

    @FXML
    private Label lblValorInicial;

    @FXML
    private Label lblValorMasAlto;

    @FXML
    private Label lblNameAnuncio;

    private Anuncio anuncio;

    public void setData(Anuncio anuncio){
        lblNameAnuncio.setText(anuncio.getTitulo());
        lblFechaPublicado.setText(String.valueOf(anuncio.getFechaPublicacion()));
        lblNomProducto.setText(anuncio.getNameProducto());
        lblTotalPujas.setText(anuncio.getTotalPujas());
        lblValorInicial.setText(anuncio.getValorInicial()+"");
        lblValorMasAlto.setText(anuncio.getValorMasAlto());
    }



    @Override
    public App getApplication() {
        return application;
    }

    @Override
    public void setApplication(App application) {
        this.application = application;
    }

    @Override
    public void inicializarComponentes() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        lblNameAnuncio.setText(anuncio.getTitulo());
        lblNomProducto.setText((anuncio.getProducto().getNombre()));
        lblFechaPublicado.setText(anuncio.getFechaPublicacion().format(formatter));
        lblValorInicial.setText("$"+anuncio.getValorInicial());
        lblValorMasAlto.setText("$"+anuncio.getValorMasAlto());
        lblTotalPujas.setText(anuncio.getTotalPujas());
    }

    @FXML
    void MostrarOpciones(MouseEvent event) {
        //verifico si el evento fue generado por el boton derecho del mouse
        if(event.getButton().toString().equals("SECONDARY")){
            //muestro el menu contextual
            application.mostrarMenuContextual();
        }
    }
}
