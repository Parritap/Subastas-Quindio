package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.Getter;
import lombok.Setter;
import model.Anuncio;
@Getter
@Setter
public class subastaItemController implements IApplication, Inicializable {

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
    public void inicializarComponentes() {}
}
