package persistencia;

import exceptions.CRUDExceptions;
import exceptions.LecturaException;
import javafx.util.converter.LocalDateStringConverter;
import model.*;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static utilities.Utils.imprimirArreglo;
import static utilities.Utils.isNot;

public class Persistencia {

    /**Escribe los atributos de un objeto en un archivo con el formato especificado
     * , solo sirve para tipos de datos primitivos, para arraylist y otros objetos se utilizan
     * metodos complementarios por cada clase
     * @param  obj objeto a serializar,
     *       este metodo sirve para cualquier tipo de objeto, siempre y cuando no teng otros objetos como atributo
     * @param ignore atributos a ignorar al momento de serializar
     * */
    public static void serializarObj(Object obj, String... ignore) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //obtiene la clase del objeto para aplicar metodos de reflexion
        Class<?> claseObj = obj.getClass();
        //escribe la cabecera en el archivo en caso de que no este
        escribirCabecera(obj, ignore);
        String atributos = "";

        Field[] camposObj= claseObj.getDeclaredFields();
        Method getter;
        for(Field campo: camposObj){
            if(isNot(campo.getName(), ignore)){
                //llama a los getter para obtener los tributos y agregarlos al resultado
            getter = claseObj.getDeclaredMethod(nombreMetodo("get", campo.getName()));
            atributos+="@@"+getter.invoke(obj);
            }
        }
        ArchivoUtil.guardarArchivo(ModelFactoryController.getRutaObjetos(obj), "\n"+atributos.substring(2), true);
    }

    /**utilidad que devuelve el nombre del metodo en base a una propiedad
     * @param prefijo prefijo del metodo (get o set)
     * @param atributo atributo (id, nombre, correo)
     * @return nombre del metodo (setName, getId, setCorreo)
     * */
    public static String nombreMetodo(String prefijo, String atributo){
        String nombreMetodo = prefijo;
        nombreMetodo+=Character.toUpperCase(atributo.charAt(0));
        nombreMetodo+=atributo.substring(1);
        return nombreMetodo;
    }

    /**escribe una cabecera al inicio del archivo para saber
     * a que propiedad corresponde cada valor de la serializacion y que tipo de dato es
     * @param obj objeto sobre el cual se basa para escribir la cabecera, p.ej
     *            si es obj es de tipo usuario, la cabecera sería name@@age@@cedula..etc
     * @param ignore atributos que quremos ignorar al momento de escribir la cabecera
     *
     * */
    public static void escribirCabecera(Object obj, String... ignore) throws IOException {
        try {
            //si la cabecera ya esta (cuando el archivo no esta vacio) devuelve y sale del metodo
            if (ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos(obj)).size() > 0) return;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        String atributos = "";
        Field[] camposObj= obj.getClass().getDeclaredFields();
        for(Field campo: camposObj){
            if(isNot(campo.getName(), ignore))
                //obtiene los nombres de los atributos y su tipo
                atributos+="@@"+campo.getName()+":"+campo.getType().getName();
        }
        ArchivoUtil.guardarArchivo(ModelFactoryController.getRutaObjetos(obj), atributos.substring(2), true);
    }


    //serializa todo el objeto empresa con productos, anuncios, usuarios y transacciones
    public static void serializarEmpresa() throws Exception {
        //obtiene la instancia de empresa
        EmpresaSubasta empresa = ModelFactoryController.getInstance();

        //se supone que deserializamos empresa de estos archivos,
        // entonces, para evitar duplicados, primero limpiamos los archivos
        ArchivoUtil.limpiarArchivo(ModelFactoryController.getRutaObjetos("Usuario.txt"));
        ArchivoUtil.limpiarArchivo(ModelFactoryController.getRutaObjetos("Puja.txt"));

        //serializa los usuarios de empresa
        for(Usuario usr: empresa.getIUsuario().getListaUsuarios())
        {
            //los serializa en cada formato
            serializarBinario(usr);
            serializarXML(usr);
            serializarUsuario(usr);}

        //hace el mismo proceso anterior pero con anuncios
        ArchivoUtil.limpiarArchivo(ModelFactoryController.getRutaObjetos("Anuncio.txt"));
        for(Anuncio anuncio: empresa.getIAnuncio().getListaAnuncios())
        {
            serializarBinario(anuncio);
            serializarXML(anuncio);
            serializarAnuncio(anuncio);}

        //hace el mismo proceso anterior pero con productos
        ArchivoUtil.limpiarArchivo(ModelFactoryController.getRutaObjetos("Producto.txt"));
        for(Producto producto: empresa.getIProducto().getListaProductos())
        {
            serializarBinario(producto);
            serializarXML(producto);
            serializarProducto(producto);}

        //crea una copia de las transacciones
        ArchivoUtil.copiarArchivo(ModelFactoryController.getRutaObjetos("Transaccion.txt"), ModelFactoryController.getRutaRespaldo("Transaccion"));
    }

    /**metodo utilizado para serializar los objetos de tipo usuario
     * @param usr usuario a serializar
     * @throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException
     * */
    public static void serializarUsuario(Usuario usr) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        //serializa los datos primitivos de usuario, ignora ciertos atributos innecesarios
        //o mas complejos como idAux, foto, listaPujas, etc...
        Persistencia.serializarObj(usr, "idAux", "foto", "listaPujas", "idAuxListaPujas");
        Persistencia.escribirCabecera(new Puja(), "usuario");
        //serializa las pujas correspondientes a este usuario, para ello pone un numeral
        //seguido de un id y todos los datos de las pujas corrspondientes a ese objeto
        ArchivoUtil.guardarArchivo(
                ModelFactoryController.getRutaObjetos("Puja.txt"), "\n#"+usr.getIdListaPujas(), true);
        for(Puja puja: usr.getListaPujas()){
            Persistencia.serializarPuja(puja);
        }

    }

    /**metodo utilizado para serializar los objetos de tipo anuncio
     * @param anuncio anuncio a serializar
     * @throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException
     * */
    public static void serializarAnuncio(Anuncio anuncio) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        //serializa los atributos base de anuncio
        serializarObj(anuncio, "idAux", "foto", "listaPujas");
        Persistencia.escribirCabecera(new Puja(), "usuario");
        //serializa las pujas correspondientes a ese anuncio en otro archivo
        ArchivoUtil.guardarArchivo(
                ModelFactoryController.getRutaObjetos("Puja.txt"), "\n#"+anuncio.getIdListaPujas(), true);
        for(Puja puja: anuncio.getListaPujas()){
            Persistencia.serializarPuja(puja);
        }
    }


    /**metodo utilizado para serializar los objetos de tipo producto
     * @param producto producto a serializar
     * @throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException
     * */
    public static void serializarProducto(Producto producto) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Persistencia.serializarObj(producto, "idAux");
    }

    /**metodo utilizado para serializar los objetos de tipo puja
     * @param puja puja a serializar
     * @throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException
     * */
    public static void serializarPuja(Puja puja) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Persistencia.serializarObj(puja, "usuario");
    }

    /**metodo utilizado para serializar los objetos de tipo transaccion
     * @param transaccion transaccion a serializar
     * @throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException
     * */
    public static void serializarTransaccion(Transaccion transaccion) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Persistencia.serializarObj(transaccion, "idAux");
    }

    /**serializa un objeto en formato XML en la ruta pedida por el taller
     * @param obj objeto a serializar
     * @throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException
     * */
    public static void serializarXML(Object obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        //obtiene la clase, a su vez esto sirve para obtener el nombre de la misma
        //y poder llamar getId
        Class<?> claseObj = obj.getClass();
        Method metodo = claseObj.getDeclaredMethod("getId");
        ArchivoUtil.salvarRecursoSerializadoXML(ModelFactoryController.getRutaSerializado(claseObj.getSimpleName() + metodo.invoke(claseObj.cast(obj)) + ".xml"), obj);
    }

    /**serializa un objeto en formato binario en la ruta pedida por el taller
     * @param obj objeto a serializar
     * @throws Exception
     * */
    public static void serializarBinario(Object obj) throws Exception {
        //similar al metodo anterior, utiliza reflexion para saber los nombres de los archivos
        Class<?> claseObj = obj.getClass();
        Method metodo = claseObj.getDeclaredMethod("getId");
        ArchivoUtil.salvarRecursoSerializado(ModelFactoryController.getRutaSerializado(claseObj.getSimpleName() + metodo.invoke(claseObj.cast(obj)) + ".dat"), obj);
    }

    /**deserializa un objeto a partir del formato implmentado del taller
     * @param obj objeto sobre el cual queremos aplicar la deserializacion
     * @param id id del objeto que queremos deserializar, el metodo busca este en todos los objetos del
     *           archivo, si no lo encuentra lanza una excepcion
     * @param idPos posicion en la cual deberia estar el id dentro del formato que manejamos
     *              p. ej nombre@@cedula@@id@@correo idPos=2
     * */
    public static void deserializarObj(Object obj, String id, Integer idPos) throws IOException, LecturaException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {

        ArrayList<String> objetos = ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos(obj));
        //la cabecera es la primera linea
        String[] cabecera = objetos.get(0).split("@@");
        String[] propiedades;
        for(String object: objetos){
            //array con las propiedades de un objeto
            propiedades = object.split("@@");
            //verificamos si el id del objeto actual es el que estamos buscando
            if(propiedades[idPos].equals(id)){
                //"quemamos" todas las propiedades del objeto actual en el objeto obj
                for(int i = 0; i<propiedades.length; i++){
                    String[] nombreTipo = cabecera[i].split(":");
                    Class<?> claseArgumento = Class.forName(nombreTipo[1]);
                    Method setter = obj.getClass().getDeclaredMethod(nombreMetodo("set", nombreTipo[0]), claseArgumento);
                    setter.invoke(obj, Persistencia.class.getDeclaredMethod("parseTo"+claseArgumento.getSimpleName(), String.class).invoke(Persistencia.class, propiedades[i]));
                }
                //salimos del metodo
                return;
            }
        }
        //si encontramos el id buscado, lanzamos una excepcion
        throw new LecturaException("objeto no encontrado", "intento fallido de deserializar objeto "+obj.getClass().getSimpleName()+" con id "+id);
    }


    /**deserializa un objeto, en vez de buscar un id en el archivo
     * le pasamos directamente la cadena con las propiedadees que queremos
     * que queme en obj
     * @param obj objeto sobre el cual queremos aplicar la deserializacion
     * @param props propiedades que queremos aplicar sobre obj
     * @throws  IOException, LecturaException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException
     * */
    public static void deserializarObj(Object obj, String props) throws IOException, LecturaException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        ArrayList<String> objetos = ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos(obj));
        //array con los nombres de los atributos
        String[] cabecera = objetos.get(0).split("@@");
        //array con las propiedades de props
        String[] propiedades = props.split("@@");
        //quemamos todas las propiedades en obj
        for(int i = 0; i<propiedades.length; i++) {
                String[] nombreTipo = cabecera[i].split(":");
                Class<?> claseArgumento = Class.forName(nombreTipo[1]);
                Method setter = obj.getClass().getDeclaredMethod(nombreMetodo("set", nombreTipo[0]), claseArgumento);
                setter.invoke(obj, Persistencia.class.getDeclaredMethod("parseTo" + claseArgumento.getSimpleName(), String.class).invoke(Persistencia.class, propiedades[i]));
            }

    }


    //METODOS NECESARIOS PARA LA DESERIALIZACION
    //PASAN DE STRING A CUALQUIER OTRO TIPO
    public static Integer parseToInteger(String dato){
        return Integer.parseInt(dato);
    }

    public static Boolean parseToBoolean(String dato){
        return Boolean.parseBoolean(dato);
    }

    public static String parseToString(String dato){
        return dato;
    }


    public static LocalDate parseToLocalDate(String dato){
        return LocalDate.parse(dato);
    }

    public static LocalDateTime parseToLocalDateTime(String dato){
        return LocalDateTime.parse(dato);
    }


    public static Estado parseToEstado(String dato) throws LecturaException {
        if(dato.equals("NUEVO")){
            return Estado.NUEVO;
        }
        else if(dato.equals("ACTUALIZADO")){
            return Estado.ACTUALIZADO;
        }
        else if(dato.equals("ELIMINADO")){
            return Estado.ELIMINADO;
        }

        throw new LecturaException("estado no valido", "Estado "+dato+" no valido");
    }

    /**deserializa todos los objetos perteneciente a empresa
     *
     * */

    public static void deserializarEmpresa() throws IOException, CRUDExceptions, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        EmpresaSubasta empresaSubasta  = ModelFactoryController.getInstance();
        //Deserializa usuarios
        empresaSubasta.setIUsuario(new IUsuario());
        ArrayList<String> usuarios = ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos("Usuario.txt"));
        for(int i=1; i<usuarios.size(); i++){
            //deserializa un usuario y lo agrega a la lista de empresa
            String props = usuarios.get(i);
            Usuario usr = new Usuario();
            deserializarUsuario(usr, props);
            empresaSubasta.getIUsuario().add(usr);
        }
        //Dserializa anuncios
        empresaSubasta.setIAnuncio(new IAnuncio());
        ArrayList<String> anuncios = ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos("Anuncio.txt"));
        for(int i=1; i<anuncios.size(); i++){
            //deserializa un anuncio y lo agrega a la lista de empresa
            String props = anuncios.get(i);
            Anuncio anuncio = new Anuncio();
            deserializarAnuncio(anuncio, props);
            empresaSubasta.getIAnuncio().add(anuncio);
        }

        //Dserializa productos
        empresaSubasta.setIProducto(new IProducto());
        ArrayList<String> productos = ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos("Producto.txt"));
        for(int i=1; i<productos.size(); i++){
            //deserializa un producto y lo agrega a la lista de empresa
            String props = productos.get(i);
            Producto producto = new Producto();
            deserializarProducto(producto, props);
            empresaSubasta.getIProducto().add(producto);
        }
    }

    /**deserializa una lista de pujas, estas siguen un formato especial, p. ej:
     * #1  //pujas de un objeto
     * john@@2020-11-09@@240
     * luisa@@2020-11-10@@300
     * john@@2020-11-11@@500
     * #2   //pujas de otro objeto
     * fernando@@2021-11-09@@360
     * miguel@@2021-11-11@@450
     * luisa@@2021-11-11@@600
     * @param idListaPujas id de la lista que queremos deserializar
     * */
    public static ArrayList<Puja> deserializarPujas(Integer idListaPujas) throws IOException, LecturaException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        ArrayList<String> pujas = ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos("Puja.txt"));
        ArrayList<Puja> resultado = new ArrayList<>();
        //variable que nos indica si ya pasamos por el id que buscamos
        Integer indicator = 0;
        for(String puja: pujas){
            if(puja.startsWith("#") && indicator == 1){
                //ya pasamos por el id buscado
                //y volvimos a pasar por otro id
                //signifca que ya pasamos por todas las oujas correspondientes a ese id
                break;
            }

            if(indicator == 1){
                //ya pasamos por el id buscado, significa que podemos deserializar la puja actual
                Puja miembroPuja = new Puja();
                deserializarObj(miembroPuja, puja);
                resultado.add(miembroPuja);
            }
            if(puja.equals("#"+idListaPujas)){
                //pasamos por el id buscado
                indicator++;
            }


        }
        return resultado;
    }

    /**deserializa un usuario buscando el id indicado en el archivo correspondiente
     * @param usr usuario sobre el cual queremos quemar las propiedades
     * @param id id que buscamos dentro del archivo
     * */
    public static void deserializarUsuario(Usuario usr, Integer id) throws LecturaException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        //deserializa los atributos base del usuario
        deserializarObj(usr, id+"", 10);
        //deserializa sus pujas correspondientes
        Integer idPujas = usr.getIdListaPujas();
        usr.setListaPujas(deserializarPujas(idPujas));
    }

    /**deserializa un usuario con base a un string con las propiedades que queremos quemar
     *  @param usr usuario sobre el cual queremos quemar las propiedades
     *  @param props string con las propiedades que queremos deserializar
     * */
    public static void deserializarUsuario(Usuario usr, String props) throws LecturaException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        //deserializa las propiedades base de usuario
        deserializarObj(usr, props);
        //deserializa las pujas correspondientes
        Integer idPujas = usr.getIdListaPujas();
        usr.setListaPujas(deserializarPujas(idPujas));
    }

    public static void deserializarAnuncio(Anuncio anuncio, Integer id) throws LecturaException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        deserializarObj(anuncio, id+"", 10);
        Integer idPujas = anuncio.getIdListaPujas();
        anuncio.setListaPujas(deserializarPujas(idPujas));
    }

    public static void deserializarAnuncio(Anuncio anuncio, String id) throws LecturaException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        deserializarObj(anuncio, id, 10);
        Integer idPujas = anuncio.getIdListaPujas();
        anuncio.setListaPujas(deserializarPujas(idPujas));
    }

    public static void deserializarProducto(Producto producto, Integer id) throws LecturaException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        deserializarObj(producto, id+"", 0);
    }

    public static void deserializarProducto(Producto producto, String props) throws LecturaException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        deserializarObj(producto, props);
    }

    /**respalda los archivos xml en la ruta pedida por el taller
     * */
    public static void respaldarXML() throws IOException {
        String rutaBase = "C:\\td";
        String[] archivos =  ArchivoUtil.listarDir(rutaBase);
        for(String archivo: archivos){
            if(archivo.endsWith(".xml")){
                ArchivoUtil.copiarArchivo(rutaBase+"\\"+archivo, ModelFactoryController.getRutaRespaldo(archivo));
            }
        }
    }
}
