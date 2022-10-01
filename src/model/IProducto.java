package model;

import exceptions.CRUDExceptions;
import interfaces.CRUD;

import java.io.Serializable;
import java.util.ArrayList;

public class IProducto implements CRUD<Producto>, Serializable {

    @Override
    public ArrayList<Producto> listar() throws CRUDExceptions {
        return null;
    }

    @Override
    public Producto buscarId(Integer id) throws CRUDExceptions {
        return null;
    }

    @Override
    public void crear(Producto obj) throws CRUDExceptions {

    }

    @Override
    public void actualizar(Integer id, Producto obj) throws CRUDExceptions {

    }

    @Override
    public void Eliminar(Integer id) throws CRUDExceptions {

    }

    @Override
    public ArrayList<Producto> listar(String campo, TipoOrden dir) throws CRUDExceptions {
        return null;
    }

    @Override
    public void add(Producto obj) throws CRUDExceptions {

    }
}
