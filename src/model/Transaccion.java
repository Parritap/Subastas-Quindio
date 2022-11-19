package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import model.enums.Estado;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
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

    public Transaccion(){}

    public boolean compararId(Integer id) {
        return this.id.compareTo(id) == 0;
    }


    public String getStringTransaccion() {
        StringBuilder arrobas = new StringBuilder("@@");
        //concateno todos los atributos separados por arroba
        return arrobas + id.toString() +
                arrobas + estado.toString() +
                arrobas + valorPago.toString() +
                arrobas + hora +
                arrobas + fecha.toString() +
                arrobas + numeroCuenta.toString() +
                arrobas + cedulaCliente +
                arrobas + codigoEmpleado +
                arrobas;
    }
}
