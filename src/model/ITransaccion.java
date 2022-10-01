package model;

import exceptions.CRUDExceptions;
import interfaces.CRUD;

import java.io.Serializable;
import java.util.ArrayList;

public class ITransaccion implements CRUD<Transaccion>, Serializable {
    @Override
    public ArrayList<Transaccion> listar() throws CRUDExceptions {
        return null;
    }

    @Override
    public Transaccion buscarId(Integer id) throws CRUDExceptions {
        return null;
    }

    @Override
    public void crear(Transaccion obj) throws CRUDExceptions {

    }

    @Override
    public void actualizar(Integer id, Transaccion obj) throws CRUDExceptions {

    }

    @Override
    public void Eliminar(Integer id) throws CRUDExceptions {

    }

    @Override
    public ArrayList<Transaccion> listar(String campo, TipoOrden dir) throws CRUDExceptions {
        return null;
    }

    @Override
    public void add(Transaccion obj) throws CRUDExceptions {

    }
}
