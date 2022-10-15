package controllers;

import application.App;
import interfaces.IApplication;
import interfaces.Inicializable;

public class controllerListadoSubastas implements IApplication, Inicializable {

    private App application;

    @Override
    public App getApplication() {
        return null;
    }

    @Override
    public void setApplication(App application) {

    }

    @Override
    public void inicializarComponentes() {}
}
