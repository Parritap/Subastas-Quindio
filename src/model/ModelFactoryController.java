package model;

import exceptions.LecturaException;
import persistencia.logic.Persistencia;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import exceptions.CRUDExceptions;
import exceptions.EscrituraException;
import java.util.Objects;

/**
 * SINGLETON
 */
public class ModelFactoryController {

    //VARIABLE GENERAL PARA TODA LA EMPRESA
    private static EmpresaSubasta empresaSubasta;
    private static Integer idListaPujas=0;
    /**
     * METODO QUE DEVUELVE LA INSTANCIA DE LA EMPRESA
     * @return LA INSTANCIA DE LA EMPRESA
     */
    public static EmpresaSubasta getInstance(){
        try {
            deserializarEmpresa();
        } catch (CRUDExceptions e) {
            throw new RuntimeException(e);
        }
        return Objects.requireNonNullElseGet(empresaSubasta, () -> {
            try {
                return empresaSubasta = new EmpresaSubasta();
            } catch (CRUDExceptions e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void deserializarEmpresa() throws CRUDExceptions {
        empresaSubasta  = Persistencia.deserializarEmpresaBinario();
        System.out.println("empresaSubasta = " + empresaSubasta);
        //hace una copia de seguridad del xml
    }

    /**da un ID para la lista de pujas, usualmente a objetos Usuario o Anuncio
     * este es necesario para deserializar
     * */
    public static Integer darIdListaPuja(){
        idListaPujas++;
        return idListaPujas;
    }
    public static ArrayList<Anuncio> getlistaAnuncios() {
        return getInstance().getListaAnuncios();
    }

    public static void crearUsuario(Usuario usuario) throws EscrituraException, IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        //serializa el usuario
        empresaSubasta.crearUsuario(usuario);
    }

    public static void crearAnuncio(Anuncio anuncio, Producto producto, Usuario clienteActivo) throws CRUDExceptions, IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        empresaSubasta.crearAnuncio(anuncio, producto, clienteActivo);
    }

    public static void actualizarUsuario(Usuario clienteActivo, Usuario usuario) throws LecturaException {
        empresaSubasta.actualizarUsuario(clienteActivo, usuario);
    }
}
