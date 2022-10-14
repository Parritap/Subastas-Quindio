package controllers;


import application.App;
import interfaces.IApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import persistencia.ArchivoUtil;
import persistencia.Persistencia;

public class PanelUsuarioController implements IApplication {
    private App application;
    @FXML
    private Text codigoText;

    @FXML
    private Button cerrar;
    public void setCodigo(String codigo){
        codigoText.setText(codigo);
    }

    @Override
    public App getApplication() {
        return this.application;
    }

    @Override
    public void setApplication(App application) {
        this.application = application;
    }

    @FXML
    public void cerrarVentana() throws Exception {
        application.cerrarStage(cerrar.getScene());
     //   application.showStageCloseAll();
    }

}
