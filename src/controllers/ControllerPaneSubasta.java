package controllers;

import application.App;
import interfaces.IApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utilities.Utils;

import java.io.ByteArrayInputStream;

public class ControllerPaneSubasta implements IApplication {


    private App application;

    //Nodos de la GUI

    @FXML
    private ImageView imgAnuncio;

    @FXML
    private TextArea txtDescripcionProducto;

    @FXML
    private TextField txtNameProduct;

    @FXML
    private TextField txtTitleAnuncio;

    @FXML
    private TextField txtValorAnuncio;

    //Estas variables contendrán la informacion ya filtrada de los campos de textos
    private byte[] bytesImg;

    //Atributos del anuncio
    private String descripcion;

    private String nombreProducto;

    private String tituloAnuncio;

    private Double valorInicialAnuncio;


    /**
     * Metodo que carga una imagen a la vista
     *
     * @param ignoredEvent generado al hacer clic
     */
    @FXML
    void cargarImagen(ActionEvent ignoredEvent) {
        byte[] imageByte = Utils.obtenerBytesImagen();
        imgAnuncio.setImage(new Image(new ByteArrayInputStream(imageByte), 199, 199, false, false));
        this.bytesImg = imageByte;
    }

    /**
     * Metodo que crea un anuncio
     *
     * @param ignoredEvent generado al hacer clic
     */
    @FXML
    void crearAnuncio(ActionEvent ignoredEvent) {
        if (cargarCamposTextos() && application.getClienteActivo() != null) {
            //Se debe crear el anuncio haciendo uso de las variables ya definidas arriba
            //modelFactoryController. crear el producto
            //crear el anuncio
            //anucio.setProducto(producto)
        }else {
            application.abrirAlerta("Debe crear una cuenta antes de publicar un anuncio");
        }

    }

    /**
     * Este metodo obtiene toda la información de los campos de texto
     * y la filtra, si alguna no cumple se debe lanzar una alerta
     * @return true si todos los campos de texto son correctos\\ false si alguno no cumple
     */
    private boolean cargarCamposTextos() {
        String mensaje = "";
        //obtengo la info de los txt
        if (!txtNameProduct.getText().equals("")) {
            nombreProducto = txtNameProduct.getText();
        } else {
            mensaje += "Debe ingresar el nombre del producto \n";
        }

        if (!txtDescripcionProducto.getText().equals("")) {
            descripcion = txtDescripcionProducto.getText();
        } else {
            mensaje += "Debe ingresar la descripcion del producto";
        }

        if (!txtTitleAnuncio.getText().equals("")) {
            tituloAnuncio = txtTitleAnuncio.getText();
        } else {
            mensaje += "Debe ingresar el titulo del anuncio";
        }

        if(!txtValorAnuncio.getText().equals("")){
            try {
                valorInicialAnuncio = Double.parseDouble(txtValorAnuncio.getText());
            }catch (NumberFormatException e){
                mensaje+="Debe ingresar valores númerico en el valor del anuncio";
            }
        }

        if(!mensaje.equals("")) application.abrirAlerta(mensaje);
        return mensaje.equals("");
    }



    //Metodos implementados por la interfaz
    @Override
    public App getApplication() {
        return application;
    }

    @Override
    public void setApplication(App application) {
        this.application = application;
    }

}
