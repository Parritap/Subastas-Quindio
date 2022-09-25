package model;

public class ModelFactoryController {

    private static EmpresaSubasta empresaSubasta;


    public static EmpresaSubasta getInstance(){

        if(empresaSubasta != null) return empresaSubasta;
        else{
           return empresaSubasta = new EmpresaSubasta();
        }

    }

}
