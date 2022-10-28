package persistencia.logic;

import exceptions.CRUDExceptions;
import exceptions.LecturaException;
import model.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import static utilities.Utils.isNot;

public class Persistencia {

    /**
     * Escribe los atributos de un objeto en un archivo con el formato especificado
     * , solo sirve para tipos de datos primitivos, para arraylist y otros objetos se utilizan
     * metodos complementarios por cada clase
     *
     * @param obj    objeto a serializar,
     *               este metodo sirve para cualquier tipo de objeto, siempre y cuando no tenga otros objetos como atributo
     * @param ignore atributos a ignorar al momento de serializar
     */
    public static void serializarObj(Object obj, String... ignore) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //obtiene la clase del objeto para aplicar metodos de reflexion
        Class<?> claseObj = obj.getClass();
        //escribe la cabecera en el archivo en caso de que no este
        escribirCabecera(obj, ignore);
        StringBuilder atributos = new StringBuilder();

        Field[] camposObj = claseObj.getDeclaredFields();
        Method getter;
        for (Field campo : camposObj) {
            if (isNot(campo.getName(), ignore)) {
                //llama a los getter para obtener los atributos y agregarlos al resultado
                getter = claseObj.getDeclaredMethod(nombreMetodo("get", campo.getName()));
                atributos.append("@@").append(getter.invoke(obj));
            }
        }
        ArchivoUtil.guardarArchivo(ModelFactoryController.getRutaObjetos(obj), "\n" + atributos.substring(2), true);
    }

    /**
     * utilidad que devuelve el nombre del metodo con base a una propiedad
     *
     * @param prefijo  prefijo del metodo (get o set)
     * @param atributo atributo (id, nombre, correo)
     * @return nombre del metodo (setName, getId, setCorreo)
     */
    public static String nombreMetodo(String prefijo, String atributo) {
        String nombreMetodo = prefijo;
        nombreMetodo += Character.toUpperCase(atributo.charAt(0));
        nombreMetodo += atributo.substring(1);
        return nombreMetodo;
    }

    /**
     * escribe una cabecera al inicio del archivo para saber
     * a que propiedad corresponde cada valor de la serializacion y que tipo de dato es
     *
     * @param obj    objeto sobre el cual se basa para escribir la cabecera, este metodo sirve para cualquier tipo de objeto,
     *               si es obj es de tipo usuario, la cabecera sería name@@age@@cedula..etc
     * @param ignore atributos que queremos ignorar al momento de escribir la cabecera
     */
    public static void escribirCabecera(Object obj, String... ignore) throws IOException {
        try {
            //si la cabecera ya está (cuando el archivo no está vacio) devuelve y sale del metodo
            if (ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos(obj)).size() > 0) return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder atributos = new StringBuilder();
        Field[] camposObj = obj.getClass().getDeclaredFields();
        for (Field campo : camposObj) {
            if (isNot(campo.getName(), ignore))
                //obtiene los nombres de los atributos y su tipo
                atributos.append("@@").append(campo.getName()).append(":").append(campo.getType().getName());
        }
        ArchivoUtil.guardarArchivo(ModelFactoryController.getRutaObjetos(obj), atributos.substring(2), true);
    }

    /**
     * guarda todo el objeto empresa en modelo.xml
     */
    public static void serializarEmpresaXML() throws Exception {
        //Persistencia.serializarEmpresa();
        ArchivoUtil.salvarRecursoSerializadoXML(ModelFactoryController.getRutaSerializado("model.xml"), deserializarEmpresa());
    }

    public static void serializarEmpresaUnificado() throws Exception {
        serializarEmpresaXML();
        serializarEmpresaBinario();
        ArchivoUtil.copiarArchivo(ModelFactoryController.getRutaObjetos("Transaccion.txt"), ModelFactoryController.getRutaRespaldo("Transaccion"));
    }

    /**
     * guarda el objeto empresa en modelo.dat
     */
    public static void serializarEmpresaBinario() throws Exception {
        ArchivoUtil.salvarRecursoSerializado(ModelFactoryController.getRutaSerializado("model.dat"), deserializarEmpresa());
    }

    public static EmpresaSubasta deserializarEmpresaBinario() throws CRUDExceptions {
        try {
            EmpresaSubasta empresa = (EmpresaSubasta) ArchivoUtil.cargarRecursoSerializado(ModelFactoryController.getRutaSerializado("model.dat"));
            if (!Objects.isNull(empresa)) return empresa;
            else return new EmpresaSubasta();
        } catch (Exception e) {
            return new EmpresaSubasta();
        }
    }

    public static EmpresaSubasta deserializarEmpresaXML() throws CRUDExceptions {
        try {
            EmpresaSubasta empresa = (EmpresaSubasta) ArchivoUtil.cargarRecursoSerializadoXML(ModelFactoryController.getRutaSerializado("model.xml"));
            if (!Objects.isNull(empresa)) return empresa;
            else throw new Exception();
        } catch (Exception e) {
            return new EmpresaSubasta();
        }
    }

    /**
     * metodo utilizado para serializar los objetos de tipo usuario
     *
     * @param usr usuario a serializar
     * @throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException
     */
    public static void serializarUsuario(Usuario usr) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        //serializa los datos primitivos de usuario, ignora ciertos atributos innecesarios
        //o mas complejos como idAux, foto, listaPujas, etc...
        Persistencia.serializarObj(usr, "idAux", "foto", "listaPujas", "idAuxListaPujas");
        Persistencia.escribirCabecera(new Puja(), "usuario");
        //serializa las pujas correspondientes a este usuario, para ello pone un numeral
        //seguido de un ID y todos los datos de las pujas correspondientes a ese objeto
        ArchivoUtil.guardarArchivo(
                ModelFactoryController.getRutaObjetos("Puja.txt"), "\n#" + usr.getIdListaPujas(), true);
        for (Puja puja : usr.getListaPujas()) {
            Persistencia.serializarPuja(puja);
        }

    }

    /**
     * metodo utilizado para serializar los objetos de tipo anuncio
     *
     * @param anuncio anuncio a serializar
     * @throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException
     */
    public static void serializarAnuncio(Anuncio anuncio) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        //serializa los atributos base de anuncio
        serializarObj(anuncio, "idAux", "foto", "listaPujas");
        Persistencia.escribirCabecera(new Puja(), "usuario");
        //serializa las pujas correspondientes a ese anuncio en otro archivo
        ArchivoUtil.guardarArchivo(
                ModelFactoryController.getRutaObjetos("Puja.txt"), "\n#" + anuncio.getIdListaPujas(), true);
        for (Puja puja : anuncio.getListaPujas()) {
            Persistencia.serializarPuja(puja);
        }
    }


    /**
     * metodo utilizado para serializar los objetos de tipo producto
     *
     * @param producto producto a serializar
     * @throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException
     */
    public static void serializarProducto(Producto producto) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Persistencia.serializarObj(producto, "idAux");
    }

    /**
     * metodo utilizado para serializar los objetos de tipo puja
     *
     * @param puja puja a serializar
     * @throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException
     */
    public static void serializarPuja(Puja puja) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Persistencia.serializarObj(puja, "usuario");
    }

    /**
     * metodo utilizado para serializar los objetos de tipo transaccion
     *
     * @param transaccion transaccion a serializar
     * @throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException
     */
    public static void serializarTransaccion(Transaccion transaccion) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Persistencia.serializarObj(transaccion, "idAux");
    }

    /**
     * serializa un objeto en formato XML en la ruta pedida por el taller
     *
     * @param obj objeto a serializar
     * @throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException
     */
    public static void serializarXML(Object obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        //obtiene la clase, a su vez esto sirve para obtener el nombre de la misma
        //y poder llamar getId
        Class<?> claseObj = obj.getClass();
        Method metodo = claseObj.getDeclaredMethod("getId");
        ArchivoUtil.salvarRecursoSerializadoXML(ModelFactoryController.getRutaSerializado(claseObj.getSimpleName() + metodo.invoke(claseObj.cast(obj)) + ".xml"), obj);
    }

    /**
     * serializa un objeto en formato binario en la ruta pedida por el taller
     *
     * @param obj objeto a serializar
     * @throws Exception si no se puede serializar
     */
    public static void serializarBinario(Object obj) throws Exception {
        //similar al metodo anterior, utiliza reflexion para saber los nombres de los archivos
        Class<?> claseObj = obj.getClass();
        Method metodo = claseObj.getDeclaredMethod("getId");
        ArchivoUtil.salvarRecursoSerializado(ModelFactoryController.getRutaSerializado(claseObj.getSimpleName() + metodo.invoke(claseObj.cast(obj)) + ".dat"), obj);
    }

    /**
     * deserialize un objeto a partir del formato implementado del taller
     *
     * @param obj   objeto sobre el cual queremos aplicar la deserialization
     * @param id    id del objeto que queremos deserializar, el metodo busca este en todos los objetos del
     *              archivo, si no lo encuentra lanza una excepcion
     * @param idPos Posición en la cual debería estar el ID dentro del formato que manejamos
     *              p. ej nombre@@cedula@@id@@correo idPos=2
     */
    public static void deserializarObj(Object obj, String id, Integer idPos) throws IOException, LecturaException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {

        ArrayList<String> objetos = ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos(obj));
        //la cabecera es la primera línea del archivo, la cual contiene los nombres de los atributos
        String[] cabecera = objetos.get(0).split("@@");
        String[] propiedades;
        for (String object : objetos) {
            //array con las propiedades de un objeto
            propiedades = object.split("@@");
            //verificamos si el ID del objeto actual es el que estamos buscando
            if (propiedades[idPos].equals(id)) {
                //"quemamos" todas las propiedades del objeto actual en el objeto obj
                for (int i = 0; i < propiedades.length; i++) {
                    String[] nombreTipo = cabecera[i].split(":");
                    Class<?> claseArgumento = Class.forName(nombreTipo[1]);
                    Method setter = obj.getClass().getDeclaredMethod(nombreMetodo("set", nombreTipo[0]), claseArgumento);
                    setter.invoke(obj, Persistencia.class.getDeclaredMethod("parseTo" + claseArgumento.getSimpleName(), String.class).invoke(Persistencia.class, propiedades[i]));
                }
                //salimos del metodo
                return;
            }
        }
        //si encontramos el ID buscado, lanzamos una excepcion
        throw new LecturaException("objeto no encontrado", "intento fallido de deserializar objeto " + obj.getClass().getSimpleName() + " con id " + id);
    }


    /**
     * deserializa un objeto, en vez de buscar un id en el archivo
     * le pasamos directamente la cadena con las propiedadees que queremos
     * que queme en obj
     *
     * @param obj   objeto sobre el cual queremos aplicar la deserializacion
     * @param props propiedades que queremos aplicar sobre obj
     * @throws IOException, LecturaException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException
     */
    public static void deserializarObj(Object obj, String props) throws IOException, LecturaException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        try {
            ArrayList<String> objetos = ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos(obj));
            //array con los nombres de los atributos
            String[] cabecera = objetos.get(0).split("@@");
            //array con las propiedades de props
            String[] propiedades = props.split("@@");
            //quemamos todas las propiedades en obj
            for (int i = 0; i < propiedades.length; i++) {
                String[] nombreTipo = cabecera[i].split(":");
                Class<?> claseArgumento = Class.forName(nombreTipo[1]);
                Method setter = obj.getClass().getDeclaredMethod(nombreMetodo("set", nombreTipo[0]), claseArgumento);
                setter.invoke(obj, Persistencia.class.getDeclaredMethod("parseTo" + claseArgumento.getSimpleName(), String.class).invoke(Persistencia.class, propiedades[i]));
            }
        } catch (Exception e) {
            Persistencia.registrarExcepcion(e, "", 3);
            e.printStackTrace();
        }

    }

    /**
     * Método que registra las excepciones dentro de un archivo .log junto con todo su StackTrace.
     * El archivo log se encuentra en src/persistencia/exceptions/registroExcepciones.log
     * En este archivo se encarga de registrar todo tipo de excepciones que se presenten en el programa.
     * @param e Excepcion a registrar
     * @param mensaje Mensaje relacionado a la excepcion
     * @param nivel nivel de la excepcion.
     */
    public static void registrarExcepcion(Exception e, String mensaje, int nivel) {
        ArchivoUtil.guardarRegistroLogExceptions(e, mensaje, nivel);
    }




    public static Estado parseToEstado(String dato) throws LecturaException {
        switch (dato) {
            case "NUEVO":
                return Estado.NUEVO;
            case "ACTUALIZADO":
                return Estado.ACTUALIZADO;
            case "ELIMINADO":
                return Estado.ELIMINADO;
        }
        throw new LecturaException("estado no valido", "Estado " + dato + " no valido");
    }

    /**
     * metodo que permite deserializar todos los objetos pertenecientes a empresa
     */

    public static EmpresaSubasta deserializarEmpresa() throws IOException, CRUDExceptions, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        EmpresaSubasta empresaSubasta = new EmpresaSubasta();
        //Deserializar usuarios
        empresaSubasta.setIUsuario(new IUsuario());
        ArrayList<String> usuarios = ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos("Usuario.txt"));
        for (int i = 1; i < usuarios.size(); i++) {
            //deserializar un usuario y lo agrega a la lista de empresas
            String props = usuarios.get(i);
            Usuario usr = new Usuario();
            deserializarUsuario(usr, props);
            empresaSubasta.getIUsuario().add(usr);
        }
        //Deserializar anuncios
        empresaSubasta.setIAnuncio(new IAnuncio());
        ArrayList<String> anuncios = ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos("Anuncio.txt"));
        for (int i = 1; i < anuncios.size(); i++) {
            //deserializar un anuncio y lo agrega a la lista de empresas
            String props = anuncios.get(i);
            Anuncio anuncio = new Anuncio();
            deserializarAnuncio(anuncio, props);
            empresaSubasta.getIAnuncio().add(anuncio);
        }

        //Deserializar productos
        empresaSubasta.setIProducto(new IProducto());
        ArrayList<String> productos = ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos("Producto.txt"));
        for (int i = 1; i < productos.size(); i++) {
            //deserialize un producto y lo agrega a la lista de empresas
            String props = productos.get(i);
            Producto producto = new Producto();
            deserializarProducto(producto, props);
            empresaSubasta.getIProducto().add(producto);
        }

        //Deserializar transacciones
        empresaSubasta.setITransaccion(new ITransaccion());
        ArrayList<String> transacciones = ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos("Transaccion.txt"));
        for (int i = 1; i < transacciones.size(); i++) {
            //deserializar una transaccion y lo agrega a la lista de empresas
            String props = transacciones.get(i);
            Transaccion transaccion = new Transaccion();
            deserializarTransaccion(transaccion, props);
            empresaSubasta.getITransaccion().add(transaccion);
        }

        return empresaSubasta;
    }

    /**
     * Deserializar una lista de pujas, estas siguen un formato especial, p. ej:
     * #1  //pujas de un objeto
     * john@@2020-11-09@@240
     * luisa@@2020-11-10@@300
     * john@@2020-11-11@@500
     * #2   //pujas de otro objeto
     * fernando@@2021-11-09@@360
     * miguel@@2021-11-11@@450
     * luisa@@2021-11-11@@600
     *
     * @param idListaPujas id de la lista que queremos deserializar
     */
    public static ArrayList<Puja> deserializarPujas(Integer idListaPujas) throws IOException, LecturaException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        ArrayList<String> pujas = ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos("Puja.txt"));
        ArrayList<Puja> resultado = new ArrayList<>();
        //variable que nos indica si ya pasamos por el id que buscamos
        int indicator = 0;
        for (String puja : pujas) {
            if (puja.startsWith("#") && indicator == 1) {
                //ya pasamos por el ID buscado
                //y volvimos a pasar por otro ID
                //significant que ya pasamos por todas las pujas correspondientes a ese ID
                break;
            }

            if (indicator == 1) {
                //ya pasamos por el ID buscado, significa que podemos deserializar la puja actual
                Puja miembroPuja = new Puja();
                deserializarObj(miembroPuja, puja);
                resultado.add(miembroPuja);
            }
            if (puja.equals("#" + idListaPujas)) {
                //pasamos por el ID buscado
                indicator++;
            }


        }
        return resultado;
    }

    /**
     * metodo que permite deserializar un usuario buscando el ID indicado en el archivo correspondiente
     *
     * @param usr usuario sobre el cual queremos quemar las propiedades
     * @param id  id que buscamos dentro del archivo
     */
    public static void deserializarUsuario(Usuario usr, Integer id) throws LecturaException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        //deserializar los atributos base del usuario
        deserializarObj(usr, id + "", 10);
        //deserializar sus pujas correspondientes
        Integer idPujas = usr.getIdListaPujas();
        usr.setListaPujas(deserializarPujas(idPujas));
    }

    /**
     * deserializar un usuario con base a un string con las propiedades que queremos quemar
     *
     * @param usr   usuario sobre el cual queremos quemar las propiedades
     * @param props string con las propiedades que queremos deserializar
     */
    public static void deserializarUsuario(Usuario usr, String props) throws LecturaException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        //deserializar las propiedades base de usuario
        deserializarObj(usr, props);
        //deserializar las pujas correspondientes
        Integer idPujas = usr.getIdListaPujas();
        usr.setListaPujas(deserializarPujas(idPujas));
    }

    public static void deserializarAnuncio(Anuncio anuncio, Integer id) throws LecturaException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        deserializarObj(anuncio, id + "", 10);
        Integer idPujas = anuncio.getIdListaPujas();
        anuncio.setListaPujas(deserializarPujas(idPujas));
    }

    public static void deserializarAnuncio(Anuncio anuncio, String props) throws LecturaException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        deserializarObj(anuncio, props);
        Integer idPujas = anuncio.getIdListaPujas();
        anuncio.setListaPujas(deserializarPujas(idPujas));
    }

    public static void deserializarProducto(Producto producto, Integer id) throws LecturaException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        deserializarObj(producto, id + "", 0);
    }

    public static void deserializarProducto(Producto producto, String props) throws LecturaException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        deserializarObj(producto, props);
    }

    public static void deserializarTransaccion(Transaccion transaccion, Integer id) throws LecturaException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        deserializarObj(transaccion, id + "", 0);
    }

    public static void deserializarTransaccion(Transaccion transaccion, String props) throws LecturaException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        deserializarObj(transaccion, props);
    }

    /**
     * respalda los archivos xml en la ruta pedida por el taller
     */
    public static void respaldarXML() {
        try {
            String rutaXML = ModelFactoryController.getRutaBase() + "\\persistencia\\model.xml";
            String rutaCopiaXML = ModelFactoryController.getRutaRespaldo("model.xml");
            File archivo = new File(rutaXML);
            if (archivo.exists()) {
                ArchivoUtil.copiarArchivo(rutaXML, rutaCopiaXML);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Este método solo debería ser usado para registrar la información de un acción, por lo que el nivel del Log será INFO.
     */
    public static void registrarAccion(String mensajeLog, String accion, String rutaArchivo) {
        ArchivoUtil.guardarRegistroLog(mensajeLog, 1 , accion, rutaArchivo);
    }

    //METODOS NECESARIOS PARA DESERIALIZAR OBJETOS
    //PASAN DE STRING A CUALQUIER OTRO TIPO
    public static Integer parseToInteger(String dato) {
        return Integer.parseInt(dato);
    }

    public static Boolean parseToBoolean(String dato) {
        return Boolean.parseBoolean(dato);
    }

    public static String parseToString(String dato) {
        return dato;
    }

    public static LocalDate parseToLocalDate(String dato) {
        return LocalDate.parse(dato);
    }

    public static LocalDateTime parseToLocalDateTime(String dato) {
        return LocalDateTime.parse(dato);
    }

    public static Double parseToDouble(String dato) {
        return Double.parseDouble(dato);
    }



}
