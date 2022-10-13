package model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
public class Transaccion implements Serializable {
    private Integer id;
    private Estado estado;
    private Integer valorPago;
    private static Integer idAux = 0;

    private LocalDate fecha;

    private Integer numeroCuenta;
    private String cedulaCliente;
    private String codigoEmpleado;

    public Transaccion()
    public Transaccion(Integer valorPago){

        this.id = ++idAux;
        this.valorPago = valorPago;
    }

    public boolean compararId(Integer id) {
        return this.id.compareTo(id) == 0;
    }
}
