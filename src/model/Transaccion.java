package model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Transaccion implements Serializable {
    private Integer id;
    private Estado estado;
    private Integer valorPago;
    private static Integer idAux = 0;
    public Transaccion(Integer valorPago){

        this.id = ++idAux;
        this.valorPago = valorPago;
    }

    public boolean compararId(Integer id) {
        return this.id.compareTo(id) == 0;
    }
}
