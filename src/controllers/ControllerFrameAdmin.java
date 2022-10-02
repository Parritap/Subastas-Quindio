package controllers;

import application.App;
import interfaces.IApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ControllerFrameAdmin implements IApplication {


    private App application;


    @Override
    public App getApplication() {
        return null;
    }

    @Override
    public void setApplication(App application) {
        this.application = application;
    }

    /**
     * METODO QUE PERMITE CAMBIAR EL STAGE E IR AL INICIO ESTANDO DESDE
     * EL FRAME DE ADMIN
     * @param event GENERADO AL HACER CLIC
     */
    @FXML
    void irInicio(ActionEvent event) {
        application.showStageCloseAll("frame inicial");
    }
}
