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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SubastaController implements IApplication {

    private App application;

    @Override
    public App getApplication() {
        return application;
    }

    @Override
    public void setApplication(App application) {
        this.application = application;
    }




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
    private List<Fruit> fruits = new ArrayList();
    private Image image;
    private MyListener myListener;

    public MarketController() {
    }

    private List<Fruit> getData() {
        List<Fruit> fruits = new ArrayList();
        Fruit fruit = new Fruit();
        fruit.setName("Kiwi");
        fruit.setPrice(2.99);
        fruit.setImgSrc("/img/kiwi.png");
        fruit.setColor("6A7324");
        fruits.add(fruit);
        fruit = new Fruit();
        fruit.setName("Coconut");
        fruit.setPrice(3.99);
        fruit.setImgSrc("/img/coconut.png");
        fruit.setColor("A7745B");
        fruits.add(fruit);
        fruit = new Fruit();
        fruit.setName("Peach");
        fruit.setPrice(1.5);
        fruit.setImgSrc("/img/peach.png");
        fruit.setColor("F16C31");
        fruits.add(fruit);
        fruit = new Fruit();
        fruit.setName("Grapes");
        fruit.setPrice(0.99);
        fruit.setImgSrc("/img/grapes.png");
        fruit.setColor("291D36");
        fruits.add(fruit);
        fruit = new Fruit();
        fruit.setName("Watermelon");
        fruit.setPrice(4.99);
        fruit.setImgSrc("/img/watermelon.png");
        fruit.setColor("22371D");
        fruits.add(fruit);
        fruit = new Fruit();
        fruit.setName("Orange");
        fruit.setPrice(2.99);
        fruit.setImgSrc("/img/orange.png");
        fruit.setColor("FB5D03");
        fruits.add(fruit);
        fruit = new Fruit();
        fruit.setName("StrawBerry");
        fruit.setPrice(0.99);
        fruit.setImgSrc("/img/strawberry.png");
        fruit.setColor("80080C");
        fruits.add(fruit);
        fruit = new Fruit();
        fruit.setName("Mango");
        fruit.setPrice(0.99);
        fruit.setImgSrc("/img/mango.png");
        fruit.setColor("FFB605");
        fruits.add(fruit);
        fruit = new Fruit();
        fruit.setName("Cherry");
        fruit.setPrice(0.99);
        fruit.setImgSrc("/img/cherry.png");
        fruit.setColor("5F060E");
        fruits.add(fruit);
        fruit = new Fruit();
        fruit.setName("Banana");
        fruit.setPrice(1.99);
        fruit.setImgSrc("/img/banana.png");
        fruit.setColor("E7C00F");
        fruits.add(fruit);
        return fruits;
    }

    private void setChosenFruit(Fruit fruit) {
        this.fruitNameLable.setText(fruit.getName());
        this.fruitPriceLabel.setText("$" + fruit.getPrice());
        this.image = new Image(this.getClass().getResourceAsStream(fruit.getImgSrc()));
        this.fruitImg.setImage(this.image);
        this.chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n    -fx-background-radius: 30;");
    }

    public void initialize(URL location, ResourceBundle resources) {
        this.fruits.addAll(this.getData());
        if (this.fruits.size() > 0) {
            this.setChosenFruit((Fruit)this.fruits.get(0));
            this.myListener = new MyListener() {
                public void onClickListener(Fruit fruit) {
                    MarketController.this.setChosenFruit(fruit);
                }
            };
        }

        int column = 0;
        int row = 1;

        try {
            for(int i = 0; i < this.fruits.size(); ++i) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(this.getClass().getResource("/views/item.fxml"));
                AnchorPane anchorPane = (AnchorPane)fxmlLoader.load();
                ItemController itemController = (ItemController)fxmlLoader.getController();
                itemController.setData((Fruit)this.fruits.get(i), this.myListener);
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
}
