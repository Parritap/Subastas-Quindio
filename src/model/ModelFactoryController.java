package model;

import java.util.Objects;

public class ModelFactoryController {

    private static EmpresaSubasta empresaSubasta;


    public static EmpresaSubasta getInstance(){
        return Objects.requireNonNullElseGet(empresaSubasta, () -> empresaSubasta = new EmpresaSubasta());
    }

}
