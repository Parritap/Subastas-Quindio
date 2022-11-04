package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;

public class ChatController implements IApplication, Inicializable {

    private App application;

    @Override
    public void inicializarComponentes() {

    }

    @Override
    public App getApplication() {
        return application;
    }

    @Override
    public void setApplication(App application) {
        this.application = application;
    }
}
