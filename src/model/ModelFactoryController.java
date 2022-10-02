package model;

import java.util.Objects;

/**
 * SINGLETON
 */
public class ModelFactoryController {

    //VARIABLE GENERAL PARA TODA LA EMPRESA
    private static EmpresaSubasta empresaSubasta;

    /**
     * METODO QUE DEVUELVE LA INSTANCIA DE LA EMPRESA
     * @return LA INSTANCIA DE LA EMPRESA
     */
    public static EmpresaSubasta getInstance(){
        return Objects.requireNonNullElseGet(empresaSubasta, () -> empresaSubasta = new EmpresaSubasta());
    }


}
