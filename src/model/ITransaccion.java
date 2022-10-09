package model;

import exceptions.CRUDExceptions;
import exceptions.EscrituraException;
import exceptions.LecturaException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ITransaccion implements CRUD<Transaccion>, Serializable {
    ArrayList<Transaccion> listaTransacciones = new ArrayList<>();
    @Override
    public ArrayList<Transaccion> listar() throws CRUDExceptions {
        if(listaTransacciones.size() >0)
            return this.listaTransacciones;
        throw new LecturaException("No hay Transacciones para listar");
    }

    @Override
    public Transaccion buscarId(Integer id) throws CRUDExceptions {
        for(Transaccion transaccion: listaTransacciones){
            if(transaccion.getId() == id){
                return transaccion;
            }
        }

        throw  new LecturaException("Transaccion con id "+id+" no encontrado");
    }



    /**utilizado para agregar una transaccion en listaTransaccion
     * @param transaccion transaccion a egregar
     * @return void
     * */
    @Override
    public void crear(Transaccion transaccion) throws CRUDExceptions, EscrituraException {
        if (noExisteTransaccion(transaccion)) {
            transaccion.setEstado(Estado.NUEVO);
            listaTransacciones.add(transaccion);
        }
    }

    /**
     * @param id id  por el cual se va a buscar el objeto a actualizar
     * */
        @Override
        public void actualizar (Integer id, Transaccion nuevaTransaccion) throws CRUDExceptions {
            for(int i=0; i< listaTransacciones.size(); i++){
                if(listaTransacciones.get(i).getId() == id){
                    listaTransacciones.set(i, nuevaTransaccion);
                }
            }
        }


        /**
         * Metodod utilizado para eliminar un elemento de listaTransaccion
         * @return void
         * @param id id del objeto que se pretende eliminar
         *
        * */
        @Override
        public void Eliminar (Integer id) throws CRUDExceptions {
            boolean flag = false;
            for (Transaccion transacion: listaTransacciones) {
                if (transacion.compararId(id)) {
                    transacion.setEstado(Estado.ELIMINADO);
                    flag = true;
                    break;
                }
            }
            if(!flag) throw new EscrituraException("No se ha podido eliminar el anuncio");
        }


        /**
         * determina quien es el mayor entre las dos trasacciones a o b
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
        public ArrayList<Transaccion> listar (String campo, TipoOrden dir) throws CRUDExceptions {
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


        /**nos dice si existe una transaccion, se utiliza en varios metodos*/
        public Boolean noExisteTransaccion(Transaccion transaccionBuscada){
            for(Transaccion transaccion: listaTransacciones) {
                if (transaccion.getId() == transaccionBuscada.getId()) {
                    return false;
                }
            }
            return true;
        }

        /**agrega una transaccion a la lista, primero verifica que no haya
         * sido agregada antes*/
        @Override
        public void add (Transaccion transaccion) throws CRUDExceptions {
            if (!noExisteTransaccion(transaccion)) {
                listaTransacciones.add(transaccion);
            }
        }
    }
