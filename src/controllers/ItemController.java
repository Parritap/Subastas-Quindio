package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.Setter;
import model.Anuncio;
import utilities.Utils;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
@Getter
@Setter
public class ItemController implements IApplication, Inicializable {

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

    //Variable para definir si se debe dejar el corazon lleno o el vacio
    private Boolean corazonLleno;

    //instancia del controlador del panel de subasta
    private  SubastaController subastaController;


    private int horas, minutos ,segundos;


    /**
     * Este metodo recibe un anuncio y lo carga en un item
     * que es mostrado como anuncio
     * @param anuncio contiene las propiedades que se va a mostrar
     */
    public void setData(Anuncio anuncio) {
        this.anuncio = anuncio;
        nameLabel.setText(anuncio.getTitulo());
        priceLable.setText(anuncio.getValorInicial()+"");
        img.setImage(new Image(new ByteArrayInputStream(anuncio.getImageSrc())));
        lblTiempo.setText("Tiempo restante "+horas+" "+minutos+" "+segundos);
        actualizarTiempo();
    }

    /**
     * Este metodo actualiza el label de cuanto tiempo lleva el anuncio
     * se usa la clase animationTimer para generar el ciclo
     */
    public void actualizarTiempo(){
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long tiempoActual) {
                //con la fecha de terminacion del anuncio se le resta 1000000 para que se actualice cada segundo
                //a su vez se le resta la fecha actual para obtener el tiempo restante
                //se cambia el texto de lblTiempo para mostrar el tiempo restante en minutos y segundos
                LocalDateTime fechaActual = LocalDateTime.now();
                LocalDateTime fechaTerminacion = anuncio.getFechaTerminacion();
                long tiempoRestante = fechaTerminacion.toEpochSecond(Utils.ZONE_OFFSET) - fechaActual.toEpochSecond(Utils.ZONE_OFFSET);
                segundos = (int) (tiempoRestante % 60);
                minutos = (int) ((tiempoRestante / 60) % 60);
                //si el tiempo se acaba, se acaba la animación
                if(segundos == 0 && minutos == 0){
                    this.stop();
                    //actualizo los anuncios contenidos
                    subastaController.actualizarAnuncios();
                    anuncio.setFueMostrado(false);
                }else {
                    lblTiempo.setText("Tiempo restante "+minutos+" minutos "+segundos +" segundos");
                }

            }
        };

        timer.start();
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
    void setProductSelected(MouseEvent ignored){
        //desacoplo los metodos
        setProductSelected();

    }

    /**
     * Cuando entra el mouse a la imagen cargo el corazon lleno
     * @param ignoredEvent generado al momento que entra el mouse
     */
    @FXML
    void cargarCorazonLleno(MouseEvent ignoredEvent) {
        heart.setImage(new Image(Utils.corazonLleno));
    }

    /**
     * Cuando el mouse sale de la imagen cargo el corazon vacio
     * @param ignoredEvent generado al entrar a la imagen
     */
    @FXML
    void cargarCorazonVacio(MouseEvent ignoredEvent) {
        heart.setImage(new Image(Utils.corazonVacio));
        if(corazonLleno) cargarCorazonLleno(null);
    }

    @FXML
    void initialize() {
        corazonLleno = false;
    }

    /**
     * Metodo que al momento de hacer clic cambia el estado de la variable
     * corazon lleno
     * @param ignoredEvent generado al momento de hacer clic
     */
    @FXML
    void definirHeart(MouseEvent ignoredEvent) {
        //cambio el valor de la variable
        corazonLleno = !corazonLleno;
    }

    /**
     * Metodo aun en proceso, cambiara el anuncio en la barra lateral
     */
    private void setProductSelected(){
        
        subastaController.setProductSelected(anuncio);
        //genero un sonido al hacer clic
        Utils.playSound(Utils.URL_CLICK);
        
    }


    @Override
    public void inicializarComponentes() {}
}
