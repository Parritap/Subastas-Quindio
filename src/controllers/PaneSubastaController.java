package controllers;

import application.App;
import exceptions.CRUDExceptions;
import interfaces.IApplication;
import interfaces.Inicializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Anuncio;
import model.ModelFactoryController;
import model.Producto;
import model.enums.TipoProducto;
import persistencia.logic.ArchivoUtil;
import utilities.Utils;
import java.io.ByteArrayInputStream;

public class PaneSubastaController implements IApplication, Inicializable {


    private App application;
    //Nodos de la GUI
    @FXML
    private Label lblTituloAnuncio;
    @FXML
    private ComboBox<String> cmbBoxTipoProducto;
    @FXML
    private Button btnCrearAnuncio;
    @FXML
    private Spinner<Integer> spinnerMinutos;
    @FXML
    private TextField txtValorMinimoPuja;
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

    private final String[] tiposProductos ={"tecnología", "hogar", "deportes", "vehículos", "bien raíz", "otros"};

    //Estas variables contendrán la informacion ya filtrada de los campos de textos
    private byte[] bytesImg;

    //Atributos del anuncio
    private String descripcion;

    private String nombreProducto;

    private String tituloAnuncio;

    private Double valorInicialAnuncio;

    private Integer minutosSubasta;

    private TipoProducto tipoProductoSelected;

    private Double valorMinimoPuja;


    /**
     * Metodo que carga una imagen a la vista
     *
     * @param ignoredEvent generado al hacer clic
     */
    @FXML
    void cargarImagen(ActionEvent ignoredEvent) {
        Utils.playSound(Utils.URL_CLICK_BUTTON);
        try {
            byte[] imageByte = Utils.obtenerBytesImagen();
            imgAnuncio.setImage(new Image(new ByteArrayInputStream(imageByte), 199, 199, false, false));
            this.bytesImg = imageByte;
        }catch (Exception ignored){}

    }

    /**
     * Metodo que crea un anuncio
     *
     * @param ignoredEvent generado al hacer clic
     */
    @FXML
    void crearAnuncio(ActionEvent ignoredEvent) {
        Utils.playSound(Utils.URL_CLICK_BUTTON);
        //si no hay un cliente activo no se puede crear un anuncio
        if(application.getClienteActivo() == null){
            application.abrirAlerta("Debe crear una cuenta antes de publicar un anuncio");
            limpiarCamposTextos();
            //si los campos de texto están completos creo el anuncio
        }else if (cargarCamposTextos()) {
            //creo el producto con los datos de la vista
            Producto producto = new Producto(nombreProducto, descripcion);
            Long l = Long.parseLong(String.valueOf(minutosSubasta));
            //creo el anuncio con los datos de la vista
            Anuncio anuncio = new Anuncio(tituloAnuncio, bytesImg, valorInicialAnuncio,l);
            anuncio.setValorMinimo(valorMinimoPuja);
            producto.setTipoProducto(tipoProductoSelected);
            //agrego el producto al anuncio
            anuncio.setProducto(producto);
            anuncio.setUsuario(application.getClienteActivo());
            application.getClienteActivo().addAnuncio(anuncio);
            try {
                //agrego el anuncio a la lista de anuncios del modelo
                ModelFactoryController.crearAnuncio(anuncio, producto, application.getClienteActivo());
                //guardo los registros log en un archivo
                ArchivoUtil.guardarRegistroLog("se creo el anuncio "+anuncio.getId()+":"+anuncio.getTitulo(), 1,
                        "Creacion de anuncio", Utils.RUTA_LOG_TXT);
                ArchivoUtil.guardarRegistroLog("se creo el producto "+producto.getId()+":"+producto.getNombre(), 1,
                        "Creacion de producto", Utils.RUTA_LOG_TXT);
                //informo al usuario que el anuncio fue creado
                application.abrirAlerta("Anuncio creado correctamente");
                //limpio los campos de texto
                limpiarCamposTextos();
            } catch (CRUDExceptions e) {
                application.abrirAlerta(e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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
        if(bytesImg == null){
            mensaje += "Agregue una imagen para el producto \n ";
        }
        if (!txtNameProduct.getText().equals("")) {
            nombreProducto = txtNameProduct.getText();
        } else {
            mensaje += "Debe ingresar el nombre del producto \n";
        }

        if (!txtDescripcionProducto.getText().equals("")) {
            descripcion = txtDescripcionProducto.getText();
        } else {
            mensaje += "Debe ingresar la descripcion del producto\n";
        }

        if (!txtTitleAnuncio.getText().equals("")) {
            tituloAnuncio = txtTitleAnuncio.getText();
        } else {
            mensaje += "Debe ingresar el titulo del anuncio\n";
        }

        if(!txtValorAnuncio.getText().equals("")){
            try {
                valorInicialAnuncio = Double.parseDouble(txtValorAnuncio.getText());
            }catch (NumberFormatException e){
                mensaje += "Debe ingresar un valor númerico para el precio inicial\n";
            }
        }else{
            mensaje+="Debe ingresar valores númerico en el valor del anuncio \n";
        }
        minutosSubasta = spinnerMinutos.getValue();

        tipoProductoSelected = getTipoProductoSelected();

        try {
            valorMinimoPuja = Double.parseDouble(txtValorMinimoPuja.getText());
        } catch (NumberFormatException e){
            mensaje += "Debe ingresar un valor númerico para el valor minimo de la puja\n";
        }
        if(tipoProductoSelected == null){
            mensaje += "Debe seleccionar un tipo de producto\n";
        }
        if(!mensaje.equals("")) application.abrirAlerta(mensaje);
        return mensaje.equals("");
    }

    /**
     * Este metodo permite limpiar los campos de texto a
     * su estado inicial
     */
    private void limpiarCamposTextos() {
        txtNameProduct.setText("");
        txtDescripcionProducto.setText("");
        txtTitleAnuncio.setText("");
        txtValorAnuncio.setText("");
        txtValorMinimoPuja.setText("");
        imgAnuncio.setImage(null);
        bytesImg = null;
        spinnerMinutos.getValueFactory().setValue(5);
        cmbBoxTipoProducto.getSelectionModel().select(0);
    }

    /**
     * Metodo que obtiene el tipo de producto seleccionado en el combobox
     * @return tipo de producto seleccionado
     */
    private TipoProducto getTipoProductoSelected() {
        if(cmbBoxTipoProducto.getValue() != null){
            String tipoProducto = cmbBoxTipoProducto.getValue();
            switch (tipoProducto){
                case "tecnología":
                    return TipoProducto.TECNOLOGIA;
                case "hogar":
                    return TipoProducto.HOGAR;
                case "deportes":
                    return TipoProducto.DEPORTES;
                case "vehículos":
                    return TipoProducto.VEHICULOS;
                case "bien raíz":
                    return TipoProducto.BIEN_RAIZ;
                case "otros":
                    return TipoProducto.OTROS;
            }
        }
        return null;
    }

    @FXML
    void initialize() {
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, 5);
        spinnerMinutos.setValueFactory(valueFactory);
        cmbBoxTipoProducto.getItems().addAll(tiposProductos);
        lblTituloAnuncio.setText("Crear anuncio");
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

    @Override
    public void inicializarComponentes() {}

    public void cargarActualizarAnuncio(Anuncio anuncioClicked) {
        lblTituloAnuncio.setText("Actualizar anuncio");
        btnCrearAnuncio.setText("Actualizar anuncio");
        txtNameProduct.setText(anuncioClicked.getProducto().getNombre());
        txtDescripcionProducto.setText(anuncioClicked.getProducto().getDescripcion());
        txtTitleAnuncio.setText(anuncioClicked.getTitulo());
        txtValorAnuncio.setText(String.valueOf(anuncioClicked.getValorInicial()));
        txtValorMinimoPuja.setText(String.valueOf(anuncioClicked.getValorMinimo()));
        spinnerMinutos.getValueFactory().setValue(anuncioClicked.getMinutosSubasta());
        cmbBoxTipoProducto.getSelectionModel().select(anuncioClicked.getProducto().getTipoProducto().toString());
        imgAnuncio.setImage(new Image(new ByteArrayInputStream(anuncioClicked.getImageSrc())));


    }
}
