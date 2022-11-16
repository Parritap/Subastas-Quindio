package persistencia.logic;

import model.ModelFactoryController;

public class ConditionsChecker extends Thread {

    @Override
    public void run (){
        while (true){
            ModelFactoryController.desactivarAnuncios();
            try {
                sleep(100); //No deseo desperdiciar muchos recursos de la CPU.
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
