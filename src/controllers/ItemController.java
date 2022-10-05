package controllers;

import application.App;
import interfaces.IApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
    private Fruit fruit;
    private MyListener myListener;

    public ItemController() {
    }

    @FXML
    private void click(MouseEvent mouseEvent) {
        this.myListener.onClickListener(this.fruit);
    }

    public void setData(Fruit fruit, MyListener myListener) {
        this.fruit = fruit;
        this.myListener = myListener;
        this.nameLabel.setText(fruit.getName());
        this.priceLable.setText("$" + fruit.getPrice());
        Image image = new Image(this.getClass().getResourceAsStream(fruit.getImgSrc()));
        this.img.setImage(image);
    }
}
