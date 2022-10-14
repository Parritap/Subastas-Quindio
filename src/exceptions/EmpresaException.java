package exceptions;


import lombok.Getter;
import lombok.Setter;
import model.ModelFactoryController;
import persistencia.ArchivoUtil;

import java.time.LocalDate;

@Getter
@Setter
public class EmpresaException extends RuntimeException{
    private static Integer nivelDeExcepcion = 2;
    private LocalDate fecha;
    //Cada excepcion toma dos mensajes, uno para imprimir en consola, otro para escribir en el log
    public EmpresaException(String mensaje,  String mensajeLog){
        super(mensaje);
        fecha= LocalDate.now();
        ArchivoUtil.guardarRegistroLog(this.getClass().getSimpleName()+", "+mensajeLog, nivelDeExcepcion, "Excepcion", ModelFactoryController.getRutaLogs("Excepciones.txt"));
    }

    public EmpresaException (){
        super();
        fecha = LocalDate.now();
    }

}
