package controllers;

import application.App;
import interfaces.IApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Anuncio;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class ItemController implements IApplication {

    //variables globales
    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    private App application;

    private Anuncio anuncio;


    /**
     * Este metodo recibe un anuncio y lo carga en un item
     * que es mostrado como anuncio
     * @param anuncio contiene las propiedades que se va a mostrar
     */
    public void setData(Anuncio anuncio) {
        this.anuncio = anuncio;
        nameLabel.setText(anuncio.getName());
        priceLable.setText(anuncio.getValorInicial()+"");
        img.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(anuncio.getImageSrc()))));
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
     * Metodo que permite que al hacer clic en un anuncio la barra lateral
     * cambie del producto seleccionado
     * @param ignored
     */
    @FXML
    public void setProductSelected(ActionEvent ignored){
        //desacoplo los metodos
        setProductSelected();

    }

    /**
     * Metodo aun en proceso, cambiara el anuncio en la barra lateral
     */
    private void setProductSelected(){
        application.setProductSelected(anuncio);
    }


}
