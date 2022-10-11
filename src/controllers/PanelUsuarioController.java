package controllers;


import application.App;
import interfaces.IApplication;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class PanelUsuarioController implements IApplication {
    private App application;
    @FXML
    private Text codigoText;
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
}
