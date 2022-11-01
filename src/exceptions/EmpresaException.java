package exceptions;


import lombok.Getter;
import lombok.Setter;
import persistencia.logic.ArchivoUtil;
import utilities.Utils;

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
        ArchivoUtil.guardarRegistroLog(this.getClass().getSimpleName()+", "+mensajeLog, nivelDeExcepcion, "Excepcion", Utils.RUTA_LOG_TXT);
    }


}
