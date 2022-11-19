package interfaces;

import application.App;
import controllers.ListadoSubastasController;

/**
 * Esta interfaz debe ser aplicada a los
 * controladores, los obliga a tener una instancia de Application,
 * con esta relación el controlador puede generar acciones en application
 * que se desencadenen en abrir otras ventanas etc.
 */
public interface IApplication {

    //instancia de application
    App application = null;

    //obtener application
    App getApplication();
    //settear application
    void setApplication(App application);

}
