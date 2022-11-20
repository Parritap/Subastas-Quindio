package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import exceptions.CRUDExceptions;
import exceptions.EscrituraException;
import exceptions.LecturaException;
import interfaces.CRUD;
import lombok.Getter;
import lombok.Setter;
import model.enums.Estado;
import model.enums.TipoOrden;

@Getter
@Setter
public class IPuja implements CRUD<Puja>, Serializable {

    private static final long serialVersionUID = 1022L;
	//SE CAMBIA EL HASHMAP POR UN ARRAYLIST, DEBIDO A QUE SE INVIRTIO LA DEPENDENCIA DEL ID
    public ArrayList<Puja> listaPujas = new ArrayList<>();
    
    public IPuja() {}
    
    /**
     * Metodo que actualiza la lista de pujas, se actualiza
     * con la lista contenida en el objeto pasado por parametro
     * se filtra para no aniadir los que ya están en la lista
     *
     * @param ipuja objeto que contiene la lista de pujas
     */
    public void actualizarPujas(IPuja ipuja) {
        for (Puja puja : ipuja.getListaPujas()) {
            if (!listaPujas.contains(puja)) {
                listaPujas.add(puja);
            }
        }
    }

    /**
     * Método que buscar por el identificador de la puja.
     *
     * @param id identificador a buscar dentro de la lista de pujas.
     * @return puja con el identificador pasado por parametro.
     * @throws LecturaException De no encontrar ningún puja con el identificador especificado.
     */
    @Override
    public Puja buscarId(Integer id) throws LecturaException {
        for (Puja listaPujas: listaPujas) {
            if (listaPujas.compararId(id)) {
                return listaPujas;
            }
        }
        throw new LecturaException("No se encontró la puja con ese ID", "puja con ID " + id + " no encontrado");
    }

    /**
     * Método que crea un puja haciendo antes las respectivas verificaciones de que no exista uno igual.
     *
     * @param puja puja a crear.
     * @throws EscrituraException Si ya existe igual al recibido en el parámetro.
     */
    @Override
    public void crear(Puja puja) throws EscrituraException {
        if (noExistePuja(puja)) {
            puja.setEstado(Estado.NUEVO);
            listaPujas.add(puja);
        }

    }

    /**
     * METODO QUE PERMITE AGREGAR UN ANUNCIO A LA EMPRESA
     *
     * @param puja ANUNCIO A AGREGAR
     * @throws CRUDExceptions SI NO PUEDE AGREGAR MAS ANUNCIO LANZA UNA EXCEPCION
     */
    @Override
    public void add(Puja puja) throws CRUDExceptions {
        crear(puja);
    }

    /**
     * Método que verifica si existe un puja antes de crearlo
     *
     * @param puja Puja a comparar.
     * @return True si el puja NO EXISTE
     */
    private boolean noExistePuja(Puja puja) {
        for (Puja aux : listaPujas) {
            if (aux.equals(puja)) {
                return false;
            }
        }
        return true;
    }

    /**
     * compara los elementos y ns dice si uno es mayor que el otro
     *
     * @param campo    atributo por el cual se van a comparar los elementos
     * @param puja1 puja a comparar
     * @param puja2 puja a comparar
     */

    public static int ordenar(String campo, Puja puja1, Puja puja2) {
        int resultado = 0;

        if ("valorInicial".equals(campo)) {
            resultado = puja1.getValorOfrecido().compareTo(puja2.getValorOfrecido());
        }
        return resultado;
    }

    /**
     * PERMITE LISTAR CON UN ORDEN
     *
     * @param campo ATRIBUTO POR EL QUE SE DESEA LISTAR
     * @param dir   ASCENDENTE O DESCENDENTE
     * @return UNA LISTA CON LOS OBJETOS ORDENADOS
     */
    @Override
    public ArrayList<Puja> listar(String campo, TipoOrden dir) {
        ArrayList<Puja> listaOrdenada = listaPujas;
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

    /**
     * Devuelve la lista de pujas almacenados en la empresa
     *
     * @return listaPujas
     */
    public ArrayList<Puja> getListaPuja() {
        //filtro los pujas para que no me retorne los que tienen el mismo id
        ArrayList<Puja> listaFiltrada = new ArrayList<>();
        for (Puja puja : listaPujas) {
            if (!listaFiltrada.contains(puja) && puja.getEstado() != Estado.ELIMINADO) {
                listaFiltrada.add(puja);
            }
        }
        return listaFiltrada;
    }

    /**
     * To string
     */
    @Override
    public String toString() {
        return "Empresa{" +
                " listaPujas=" + listaPujas.toString() +
                '}';
    }

    /**
     * Se encarga de recorrer la lista de pujas y devolver un string con la informacion de cada puja
     *
     * @return String con la informacion de cada puja
     */
    public String getStringPujas() {
        StringBuilder pujas = new StringBuilder();
        for (Puja puja : listaPujas) {
            pujas.append(puja.getStringPuja()).append("\n");
        }
        return pujas.toString();
    }

    /**
     * Metodo que devuelve la lista pujas de un usuario específico
     *
     * @param clienteActivo es el usuario que solicita su listaPujas
     * @return una lista de pujas
     */
    public ArrayList<Puja> getListaPuja(Usuario clienteActivo) {
        ArrayList<Puja> listaPujasCliente = new ArrayList<>();
        for (Puja puja : listaPujas) {
            if (puja.getUsuario().equals(clienteActivo)) {
                listaPujasCliente.add(puja);
            }
        }
        return listaPujasCliente;
    }

    /**
     * Actualizo el estado del pujaClicker
     *
     * @param pujaClicked puja que se va a actualizar
     */
    public void eliminar(Puja pujaClicked) {
        //busco el puja en la lista y lo cambio su estado ha eliminado
        pujaClicked.setEstado(Estado.ELIMINADO);
    }



   
   
    public ArrayList<Puja> filtrarPujaPorAsuario(Usuario u) {
        //En caso de que no haya ningún usuario activo, esto es, que no haya iniciado sesión, se devolverá la lista de pujas completa.
        if(u==null) return this.listaPujas;

        ArrayList<Puja> lista = new ArrayList<>();
        for (Puja a: listaPujas) {
            if (!a.getUsuario().equals(u)) lista.add(a);
        }
        return lista;
        }
}

