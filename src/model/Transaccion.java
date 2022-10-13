package model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class Transaccion implements Serializable {
    private Integer id;
    private Estado estado;
    private Integer valorPago;
    private static Integer idAux = 0;

    private String hora;
    private LocalDateTime fecha;

    private Integer numeroCuenta;
    private String cedulaCliente;
    private String codigoEmpleado;

    public Transaccion(Integer id, LocalDateTime fecha, Integer valorPago, Integer numeroCuenta, String cedulaCliente, String codigoEmpleado){
        this.id = id;
        this.fecha = fecha;
        this.hora = fecha.getHour()+":"+fecha.getMinute()+"."+fecha.getNano();
        this.valorPago = valorPago;
        this.numeroCuenta = numeroCuenta;
        this.cedulaCliente = cedulaCliente;
        this.codigoEmpleado = codigoEmpleado;
    }
    public Transaccion(Integer valorPago){

        this.id = ++idAux;
        this.valorPago = valorPago;
    }

    public boolean compararId(Integer id) {
        return this.id.compareTo(id) == 0;
    }
}
