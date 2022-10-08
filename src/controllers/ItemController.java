package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Anuncio;

import java.util.Objects;

public class ItemController implements IApplication {

    private App application;

    @Override
    public App getApplication() {
        return null;
    }

    @Override
    public void setApplication(App application) {

    }


    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLable;
    @FXML
    private ImageView img;
    private Anuncio anuncio;
    private MyListener myListener;

    public ItemController() {
    }

    @FXML
    private void click(MouseEvent mouseEvent) {
        this.myListener.onClickListener(this.anuncio);
    }

    public void setData(Anuncio anuncio, MyListener myListener) {
        this.anuncio = anuncio;
        this.myListener = myListener;
        this.nameLabel.setText(anuncio.getName());
        this.priceLable.setText("$" + anuncio.getValorInicial());
        Image image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(anuncio.getFoto().toString())));
        this.img.setImage(image);
    }
}
