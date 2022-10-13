package persistencia;

import exceptions.LecturaException;
import javafx.util.converter.LocalDateStringConverter;
import model.*;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;

import static utilities.Utils.isNot;

public class Persistencia {

    /**Escribe los atributos de un objeto en un archivo con el formato especificado
     * @param  obj objeto a serializar,
     *       este metodo sirve para cualquier tipo de objeto, siempre y cuando no teng otros objetos como atributo
     * @param ignore atributos a ignorar al momento de serializar
     * */
    public static void serializarObj(Object obj, String... ignore) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> claseObj = obj.getClass();
        escribirCabecera(obj, ignore);
        String atributos = "";

        Field[] camposObj= claseObj.getDeclaredFields();
        Method getter;
        for(Field campo: camposObj){
            if(isNot(campo.getName(), ignore)){
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
     * a que propiedad corresponde cada valor de la serializacion
     * @param obj objeto sobre el cual se basa para escribir la cabecera, p.ej
     *            si es obj es de tipo usuario, la cabecera sería name@@age@@cedula..etc
     * @param ignore atributos que quremos ignorar al momento de escribir la cabecera
     *
     * */
    public static void escribirCabecera(Object obj, String... ignore) throws IOException {
        try {
            if (ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos(obj)).size() > 0) return;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        String atributos = "";
        Field[] camposObj= obj.getClass().getDeclaredFields();
        for(Field campo: camposObj){
            if(isNot(campo.getName(), ignore))
                atributos+="@@"+campo.getName()+":"+campo.getType().getName();
        }
        ArchivoUtil.guardarArchivo(ModelFactoryController.getRutaObjetos(obj), atributos.substring(2), true);
    }


    /*public static void serializarEmpresa(){
        ModelFactoryController.getInstance().get;
    }*/

    public static void serializarUsuario(Usuario usr) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Persistencia.serializarObj(usr, "idAux", "foto", "listaPujas", "idAuxListaPujas");
        Persistencia.escribirCabecera(new Puja(), "usuario");
        ArchivoUtil.guardarArchivo(
                ModelFactoryController.getRutaObjetos("Puja.txt"), "\n#"+usr.getIdListaPujas(), true);
        for(Puja puja: usr.getListaPujas()){
            Persistencia.serializarPuja(puja);
        }

    }

    public static void serializarAnuncio(Anuncio anuncio) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        serializarObj(anuncio, "idAux", "foto", "listaPujas");
        Persistencia.escribirCabecera(new Puja(), "usuario");
        ArchivoUtil.guardarArchivo(
                ModelFactoryController.getRutaObjetos("Puja.txt"), "\n#"+anuncio.getIdListaPujas(), true);
        for(Puja puja: anuncio.getListaPujas()){
            Persistencia.serializarPuja(puja);
        }
    }

    public static void serializarProducto(Producto producto) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Persistencia.serializarObj(producto);
    }
    public static void serializarPuja(Puja puja) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Persistencia.serializarObj(puja, "usuario");
    }




    public static void deserializarObj(Object obj, String id, Integer idPos) throws IOException, LecturaException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        ArrayList<String> objetos = ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos(obj));
        String[] cabecera = objetos.get(0).split("@@");
        String[] propiedades;
        for(String object: objetos){
            propiedades = object.split("@@");
            if(propiedades[idPos].equals(id)){
                for(int i = 0; i<propiedades.length; i++){
                    String[] nombreTipo = cabecera[i].split(":");
                    Class<?> claseArgumento = Class.forName(nombreTipo[1]);
                    Method setter = obj.getClass().getDeclaredMethod(nombreMetodo("set", nombreTipo[0]), claseArgumento);
                    setter.invoke(obj, Persistencia.class.getDeclaredMethod("parseTo"+claseArgumento.getSimpleName(), String.class).invoke(Persistencia.class, propiedades[i]));
                }
                return;
            }
        }

        throw new LecturaException("usuario no encontrado", "intento fallido de deserializar usuario con id "+id);
    }


    public static void deserializarObj(Object obj, String props) throws IOException, LecturaException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        ArrayList<String> objetos = ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos(obj));
        String[] cabecera = objetos.get(0).split("@@");
        String[] propiedades = props.split("@@");
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

    public static ArrayList<Puja> deserializarPujas(Integer idListaPujas) throws IOException, LecturaException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        ArrayList<String> pujas = ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos("Puja.txt"));
        ArrayList<Puja> resultado = new ArrayList<>();
        Integer indicator = 0;
        for(String puja: pujas){

            if(puja.startsWith("#") && indicator == 1){
                break;
            }
            if(indicator == 1){
                Puja miembroPuja = new Puja();
                deserializarObj(miembroPuja, puja);
                resultado.add(miembroPuja);
            }
            if(puja.equals("#"+idListaPujas)){
                indicator++;
            }


        }
        return resultado;
    }

    public static void deserializarUsuario(Usuario usr, String id) throws LecturaException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        deserializarObj(usr, id, 10);
        Integer idPujas = usr.getIdListaPujas();
        usr.setListaPujas(deserializarPujas(idPujas));
    }

    public static void deserializarAnuncio(Anuncio anuncio, String id) throws LecturaException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        deserializarObj(anuncio, id, 10);
        Integer idPujas = anuncio.getIdListaPujas();
        anuncio.setListaPujas(deserializarPujas(idPujas));
    }


}
