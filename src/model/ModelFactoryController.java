package model;

import exceptions.LecturaException;
import persistencia.logic.Persistencia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import exceptions.CRUDExceptions;
import exceptions.EscrituraException;
import utilities.Utils;

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
   
        return Objects.requireNonNullElseGet(empresaSubasta, () -> {
            try {
                return empresaSubasta = new EmpresaSubasta();
            } catch (CRUDExceptions e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void deserializarEmpresa() throws CRUDExceptions, IOException {
//       EmpresaSubasta empresaSubastaAux = Persistencia.deserializarEmpresaBinario();
    	empresaSubasta = Persistencia.deserializarEmpresaXML();
        //actualizo las variables inscritas dentro de empresa
        //if(empresaSubasta != null && empresaSubastaAux != null) empresaSubasta.actualizarImplementaciones(empresaSubastaAux);

        //hace una copia de seguridad del xml
    }

    /**da un ID para la lista de pujas, usualmente a objetos Usuario o Anuncio
     * este es necesario para deserializar
     * */
    public static Integer darIdListaPuja(){
        idListaPujas++;
        return idListaPujas;
    }

    /**
     * Metodo que devuelve la lista de usuarios contenida en la empresa
     * @return lista de usuarios
     */
    public static ArrayList<Anuncio> getlistaAnuncios() {
        return getInstance().getListaAnuncios();
    }

    /**
     * metodo que permite crear un usuario, el usuario se crea en la instancia de la empresa contenida
     * en el singleton
     * @param usuario el usuario que se va a crear
     * @throws EscrituraException si no se puede crear el usuario
     */
    public static void crearUsuario(Usuario usuario) throws EscrituraException{
        empresaSubasta.crearUsuario(usuario);
    }

    /**
     * Metodo que permite crear anuncios en el singleton
     * @param anuncio que se va a crear
     * @param producto el producto que contiene el anuncio
     * @param clienteActivo el cliente que lo creo
     */
    public static void crearAnuncio(Anuncio anuncio, Producto producto, Usuario clienteActivo) throws CRUDExceptions{
        empresaSubasta.crearAnuncio(anuncio, producto, clienteActivo);
    }

    /**
     * Permite actualizar la informacion de un usuario
     * @param clienteActivo el cliente activo con su informacion actual
     * @param usuario un objeto usuario auxiliar que contiene la informacion a actualizar
     * @throws LecturaException si no se puede actualizar
     */
    public static void actualizarUsuario(Usuario clienteActivo, Usuario usuario) throws LecturaException {
        empresaSubasta.actualizarUsuario(clienteActivo, usuario);
    }

    /**
     * Este metodo devuelve un String con la forma de serializar todos los usuarios
     * @return String con la lista de usuarios serializada
     */
    public static String getStringUsuarios() {
        return empresaSubasta.getStringUsuarios();
    }

    /**
     * Este metodo devuelve un String con la forma de serializar todos los anuncios
     * @return String con la lista de anuncios serializada
     */
    public static String getStringProductos() {
        return empresaSubasta.getStringProductos();
    }

    /**
     * Este metodo devuelve un String con la forma de serializar todos los productos
     * @return String con la lista de productos serializada
     */
    public static String getStringTransacciones() {
        return empresaSubasta.getStringTransacciones();
    }

    public static String getStringAnuncios() {
        return empresaSubasta.getStringAnuncios();
    }
    
    
    public static String getStringPujas() {
        return empresaSubasta.getStringPujas();
    }
    
    /**
     * Metodo que devuelve todos los anuncios de un cliente
     * @param clienteActivo el cliente que creo los anuncios
     * @return una lista de anuncios
     */
    public static ArrayList<Anuncio> getlistaAnuncios(Usuario clienteActivo) {
        return empresaSubasta.getListaAnuncios(clienteActivo);
    }

    public static void eliminarAnuncio(Anuncio anuncioClicked) {
        empresaSubasta.eliminarAnuncio(anuncioClicked);
    }

    public static void actualizarAnuncio(Anuncio anuncio, Producto producto) {
        empresaSubasta.actualizarAnuncio(anuncio, producto);
    }

    public static void desactivarAnuncios() {
        empresaSubasta.getIAnuncio().desactivarAnuncios();
    }

    public static void hacerPuja (Usuario usuario, Anuncio anuncio, Double valorPuja) {

        try {
			empresaSubasta.hacerPuja(usuario, anuncio, valorPuja);
		} catch (EscrituraException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public static ArrayList<Anuncio> obtenerListaAnunciosSegunUsuario(Usuario u) {

        return empresaSubasta.getIAnuncio().filtrarAnuncioPorAsuario(u);

    }



    public static void addDatosPrueba(){

        IUsuario iUsuario = empresaSubasta.getIUsuario();
        IAnuncio iAnuncio = empresaSubasta.getIAnuncio();



        /////ESTO ES PARA NO ESTAR CREANDO USUARIOS TODO EL TIEMPO MIENTRAS ARREGLAMOS LA PERSISTENCIA -- COLOCAR UN BOOKMARK////////

        Usuario u1 = new Usuario("Parra", 21, "1002656555", "parra", "Cr14#09-18", "3243585508", "parra");
        Usuario u2 = new Usuario("Ana", 21, "1002656556", "ana", "Cr14#09-18", "3243585508", "ana");
        iUsuario.getListaUsuarios().add(u1);
        iUsuario.getListaUsuarios().add(u2);

        byte [] img1 = new byte[0];
        byte [] img2 = new byte[0];
        try {
            img1 = Files.readAllBytes(Path.of("src/resources/visa.png"));
            img2 = Files.readAllBytes(Path.of("src/resources/beagle.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Producto p1 = new Producto("Beagle", "Este es un beagle");
        Producto p2 = new Producto("MasterCard", "Este es una Mastercard");
        

        //Anuncio a1 = new Anuncio("Flying Beagle", img1, 100D, 60L);
        //Anuncio a2 = new Anuncio("MasterCard", img2, 100D, 60L);
        
        Anuncio a1 = new Anuncio("Flying Beagle", 100D, 60L, "src/resources/visa.png");
        Anuncio a2 = new Anuncio("MasterCard", 100D, 60L, "src/resources/beagle.jpg");


        try {
            ModelFactoryController.crearAnuncio(a1, p1,  u1);
            ModelFactoryController.crearAnuncio(a2, p2,  u2);
        } catch (CRUDExceptions e) {
            e.printStackTrace();
        }



        Puja  p11 = new Puja(LocalDateTime.now(), u1, 200D, a1);
        Puja  p12 = new Puja(LocalDateTime.now(), u1, 300D, a1);

        Puja  p21 = new Puja(LocalDateTime.now(), u2, 200D, a2);
        Puja  p22 = new Puja(LocalDateTime.now(), u2, 300D, a2);

        u1.getListaPujas().add(p11);
        u1.getListaPujas().add(p12);

        u2.getListaPujas().add(p21);
        u2.getListaPujas().add(p22);

        a1.getListaPujas().add(p11);
        a1.getListaPujas().add(p12);

        a2.getListaPujas().add(p21);
        a2.getListaPujas().add(p22);

        iAnuncio.getListaAnuncio().add(a1);
        iAnuncio.getListaAnuncio().add(a2);
    }

    /**
     * Retorna una lista de pujas de un usuario pasado como parámetro.
     * @param clienteActivo cliente que se supone es el activo.
     * @return Lista pujas
     */
    public static ArrayList<Puja> getListaPujas(Usuario clienteActivo) {
        return getInstance().getIUsuario().getListaPujas(clienteActivo);
    }

    public static void eliminarPuja(Puja puja) {
        empresaSubasta.eliminarPuja(puja);
    }



    /**
     * Crea un archivo de extensión .csv (tipo excel) en la ruta especificada.
     * @param ruta Ruta en la que se generará el archivo.
     * @throws IOException
     */
    public static void generarRegistrosAnunciosCSV (String ruta) {
        empresaSubasta.getIAnuncio().generarCSV(ruta);
    }
    public static void generarRegistrosAnunciosCSV (Usuario usuario, String ruta) {
        empresaSubasta.getIAnuncio().generarCSV(usuario, ruta);
    }

    public static void pruebaCrearEmpresa () throws CRUDExceptions {
        empresaSubasta = new EmpresaSubasta();
        addDatosPrueba();
    }

}
