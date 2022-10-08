package controllers;

import application.App;
import interfaces.IApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Anuncio;

import java.awt.event.ActionEvent;
import java.util.Objects;

public class ItemController implements IApplication {

    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    private App application;

    private Anuncio anuncio;


    public void setData(Anuncio anuncio) {
        this.anuncio = anuncio;
        nameLabel.setText(anuncio.getName());
        priceLable.setText(anuncio.getValorInicial()+"");
        img.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(anuncio.getImageSrc()))));
    }

    @Override
    public App getApplication() {
        return application;
    }

    @Override
    public void setApplication(App application) {
        this.application = application;
    }

    @FXML
    public void setProductSelected(ActionEvent ignored){

        setProductSelected();

    }

    private void setProductSelected(){
        application.setProductSelected(anuncio);
    }
}
