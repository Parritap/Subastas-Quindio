package model;

import exceptions.CRUDExceptions;
import exceptions.LecturaException;
import interfaces.CRUD;
import lombok.Getter;
import lombok.Setter;
import model.enums.Estado;
import model.enums.TipoOrden;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter
public class ITransaccion implements CRUD<Transaccion>, Serializable {
	
	private static final long serialVersionUID = 50L;
    ArrayList<Transaccion> listaTransacciones = new ArrayList<>();



    public ITransaccion() {}
    @Override
    public Transaccion buscarId(Integer id) throws CRUDExceptions {
        for (Transaccion transaccion : listaTransacciones) {
            if (Objects.equals(transaccion.getId(), id)) {
                return transaccion;
            }
        }
        throw  new LecturaException("Transaccion no encontrada ","Transaccion con id "+id+" no encontrado");
    }


    /**nos dice si existe una transaccion, se utiliza en varios metodos
     * @param transaccionBuscada    TRANSACCION QUE QUEREMOS VERIFICAR SI EXISTE*/
    public Boolean noExisteTransaccion(Transaccion transaccionBuscada){
        for(Transaccion transaccion: listaTransacciones) {
            if (Objects.equals(transaccion.getId(), transaccionBuscada.getId())) {
                return false;
            }
        }
        return true;
    }

    /**utilizado para agregar una transaccion en listaTransaccion
     * @param transaccion transaccion a agregar
     */
    @Override
    public void crear(Transaccion transaccion) throws CRUDExceptions {
        if (noExisteTransaccion(transaccion)) {
            transaccion.setEstado(Estado.NUEVO);
            listaTransacciones.add(transaccion);
        }
    }

    /**
     * @param id id por el cual se va a buscar el objeto a actualizar
     * */
    public void actualizar (Integer id, Transaccion nuevaTransaccion) throws CRUDExceptions {
        for(int i=0; i< listaTransacciones.size(); i++){
            if(Objects.equals(listaTransacciones.get(i).getId(), id)){
                listaTransacciones.set(i, nuevaTransaccion);
            }
        }
    }
    /**
     * determina quien es el mayor entre las dos transacciones a o b
     * @param campo atributo por el cual se van a comparar las dos transacciones
     * */

    public static int ordenar(String campo, Transaccion a, Transaccion b) {
        int resultado = 0;

        switch (campo) {
            case "id" -> resultado = a.getId().compareTo(b.getId());
            case "valorPago" -> resultado = a.getValorPago().compareTo(b.getValorPago());
        }
        return resultado;
    }

    /**
     * lista las transacciones de manera ordenada
     * dependiendo del atributo ppr el cual queramos comparar
     * @param campo atributo sobre el cual se compara
     * @param dir tipo de orden, si es descendente o ascendente
     * */
    @Override
    public ArrayList<Transaccion> listar (String campo, TipoOrden dir){
        ArrayList<Transaccion> listaOrdenada = listaTransacciones;
        listaOrdenada.sort((a, b) -> {
            int resultado = 0;
            if (dir == TipoOrden.ASCENDENTE) {
                resultado = ordenar(campo, a, b);
            } else if (dir == TipoOrden.DESCENDENTE) {
                resultado = ordenar(campo, b, a);
            }
            return resultado;
        });
        return listaOrdenada;
    }

    /**agrega una transaccion a la lista, primero verifica que no haya
     * sido agregada antes*/
    @Override
    public void add (Transaccion transaccion) throws CRUDExceptions {
        if (!noExisteTransaccion(transaccion)) {
            listaTransacciones.add(transaccion);
        }
    }
    /**
     * To string de la clase
     */

    @Override
    public String toString() {
        return "ITransaccion{" +
                "listaTransacciones=" + listaTransacciones.toString() +
                '}';
    }

    /**
     * Metodo que actualiza la lista productos, que
     * se contienen en el Iproducto pasados por parametros
     * @param iTransaccion objeto que contiene la lista de los productos
     */
    public void actualizarTransaccion(ITransaccion iTransaccion) {
        for (Transaccion transaccion : iTransaccion.getListaTransacciones()) {
            if (!listaTransacciones.contains(transaccion)) {
                listaTransacciones.add(transaccion);
            }
        }
    }

    /**
     * Metodo que devuelve un string con toda la informacion de la lista de transacciones
     * @return String con toda la informacion de la lista de transacciones
     */
    public String getStringTransacciones() {
        StringBuilder stringUsuarios = new StringBuilder();
        for (Transaccion transaccion : listaTransacciones) {
            stringUsuarios.append(transaccion.getStringTransaccion());
            stringUsuarios.append("\n");
        }
        return stringUsuarios.toString();
    }
}
