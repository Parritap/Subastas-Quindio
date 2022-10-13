package controllers;

import application.App;
import interfaces.IApplication;

public class ControllerPaneSubasta implements IApplication {


    private App application;


    @Override
    public App getApplication() {
        return application;
    }

    @Override
    public void setApplication(App application) {
        this.application = application;
    }
}
