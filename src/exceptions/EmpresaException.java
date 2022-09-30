package exceptions;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmpresaException extends RuntimeException{
    private LocalDate fecha;
    public EmpresaException(String mensaje){
        super(mensaje);
        fecha= LocalDate.now();
    }

}
