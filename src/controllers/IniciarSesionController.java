package controllers;

import application.App;
import exceptions.LecturaException;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import persistencia.logic.Persistencia;
import utilities.Utils;


@Setter
@Getter
public class IniciarSesionController implements IApplication, Inicializable {

    private App application;

    private String email;
    private String contrasenia;
    @FXML
    private Circle circulo1;

    @FXML
    private Circle circulo2;
    @FXML
    private Circle circulo3;
    @FXML
    private Circle circulo4;
    @FXML
    private Circle circulo5;
    @FXML
    private Circle circulo6;

    private Circle [] circulos;

    @FXML
    private MediaView mediaEffect;

    @FXML
    private Button btn_iniciarSesion;

    @FXML
    private TextField txtField_email;

    @FXML
    private PasswordField txt_contrasenia;

    @FXML
    void iniciarSesion(ActionEvent event) {
        try {
            if (obtenerDatos()) {
                limpiarCampos();
                application.iniciarSesion(email, contrasenia);
            }
        } catch (LecturaException e) {
            limpiarCampos();
            Persistencia.registrarExcepcion(e, "Error al iniciar sesion, credenciales incorrectas", 1);
            application.abrirAlerta("La contraseña o el email son incorrectos, por favor intente de nuevo");
        }

    }

    /**
     *Este metodo se encarga de limpia los campos de texto
     */
    void limpiarCampos() {
        txtField_email.setText("");
        txt_contrasenia.setText("");
    }


    /**
     * Método que extrae los datos de los botones.
     */
    private boolean obtenerDatos() {
        email = txtField_email.getText();
        contrasenia = txt_contrasenia.getText();

        if (email.isEmpty() || contrasenia.isEmpty()) {
            application.abrirAlerta("Alguno de los campos está vacío");
            return false;
        }
        return true;
    }

    @FXML
    void initialize() {
       circulos = new Circle[]{circulo1, circulo2, circulo3, circulo4, circulo5, circulo6};
    }

    /**
     * Metodo que realiza un fade in en el circulo
     */
     private void fadeInCircles() {
         while (true){

             new Thread(() -> {
                 //genero un tiempo aleatorio entre 8 y 20
                 int tiempoAleatorio = (int) (Math.random() * 12 + 8);
                 //genero un color RGB aleatorio
                 int colorAleatorio = (int) (Math.random() * 255);
                 int colorAleatorio2 = (int) (Math.random() * 255);
                 int colorAleatorio3 = (int) (Math.random() * 255);
                 //obtengo el circulo que se va a animar
                 Circle circulo = circulos[((int) (Math.random() * 6))];
                 //le asigno el radio aleatorio
                 circulo.setRadius(((int) (Math.random() * 20 + 10)));
                 //le asigno el color aleatorio
                 circulo.setFill(Utils.rgbToColor(colorAleatorio, colorAleatorio2, colorAleatorio3));
                 //asigno una posición aleatoria
                 circulo.setCenterX(((int) (Math.random() * 1000)));
                 circulo.setCenterY(((int) (Math.random() * 1000)));
                 //verifico que el círculo no se salga de la pantalla
                 if (circulo.getCenterX() + circulo.getRadius() > 1000) {
                        circulo.setCenterX(circulo.getCenterX() - circulo.getRadius());
                 }
                 fadeInSingularCircle(circulo, tiempoAleatorio);
             }).start();
         }


     }

     private void fadeInSingularCircle(Circle circulo1, int duration){
         FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duration), circulo1);
         fadeTransition.setFromValue(0.0);
         fadeTransition.setToValue(0.3);
         fadeTransition.setCycleCount(1);
         fadeTransition.setAutoReverse(true);
         fadeTransition.play();

     }

    public void irLogin(ActionEvent ignoredEvent) {
        application.loadScene(Utils.frameInicio);
    }



    @Override
    public App getApplication() {
        return this.application;
    }

    @Override
    public void setApplication(App application) {
        this.application = application;
    }

    @Override
    public void inicializarComponentes() {

    }
    @FXML
    public void lanzarAlerta(ActionEvent event) {
        application.abrirAlerta("Te la creiste. JAJAJ :D");
    }

}
