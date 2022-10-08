package controllers;

import application.App;
import interfaces.IApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Anuncio;
import model.ModelFactoryController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class SubastaController implements IApplication{

    private App application;
    @FXML
    private VBox chosenFruitCard;
    @FXML
    private Label fruitNameLable;
    @FXML
    private Label fruitPriceLabel;
    @FXML
    private ImageView fruitImg;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    private ArrayList<Anuncio> listaAnuncios = new ArrayList<>();
    private Image image;



    private ArrayList<Anuncio> getData() {
        return  ModelFactoryController.getInstance().getListaAnuncios();
    }

    private void loadFirstAd(Anuncio anuncio) {
        this.fruitNameLable.setText(anuncio.getName());
        this.fruitPriceLabel.setText("$" + anuncio.getValorInicial());
        this.image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(anuncio.getImageSrc())));
        this.fruitImg.setImage(this.image);
    }

    @FXML
    public void initialize() {
        inicializar();

    }

    private void inicializar() {
        
        
        
        this.listaAnuncios.addAll(ModelFactoryController.getlistaAnuncios());
        if (this.listaAnuncios.size() > 0) {
            this.loadFirstAd(this.listaAnuncios.get(0));
        }

        int column = 0;
        int row = 1;

        try {
            for (Anuncio anuncio : this.listaAnuncios) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(this.getClass().getResource("../view/AnuncioItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(anuncio);
                if (column == 3) {
                    column = 0;
                    ++row;
                }

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


    @Override
    public App getApplication() {
        return application;
    }

    @Override
    public void setApplication(App application) {
        this.application = application;
    }




}
