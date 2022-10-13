package controllers;

import application.App;
import interfaces.IApplication;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
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

    @FXML
    private Label lblTiempo;

    @FXML
    private ImageView heart;

    private App application;

    private Anuncio anuncio;

    private int horas, minutos ,segundos;


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
        lblTiempo.setText("Tiempo restante "+horas+" "+minutos+" "+segundos);
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
     * @param ignored evento generado al hacer clin
     */
    @FXML
    public void setProductSelected(ActionEvent ignored){
        //desacoplo los metodos
        setProductSelected();

    }


    @FXML
    void cargarCorazonLleno(MouseEvent event) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(5000));
        fade.setFromValue(10);
        fade.setToValue(0.1);
        fade.setNode(heart);

        System.out.println("cargo corazon lleno");
        heart.setImage(new Image("/resources/heart.jpg"));
        fade.play();
    }

    @FXML
    void cargarCorazonVacio(MouseEvent event) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(5000));
        fade.setFromValue(10);
        fade.setToValue(0.1);
        fade.setNode(heart);

        System.out.println("cargo corazon vacio");
        heart.setImage(new Image("/resources/heartContorno.png"));
        fade.play();
    }

    @FXML
    void initialize() {

    }


    /**
     * Metodo aun en proceso, cambiara el anuncio en la barra lateral
     */
    private void setProductSelected(){
        application.setProductSelected(anuncio);
    }


}
