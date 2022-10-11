package persistencia;

import exceptions.LecturaException;
import model.Estado;
import model.ModelFactoryController;
import model.Usuario;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        try {
            if (ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos(obj)).size() == 0)
                escribirCabecera(obj, ignore);
        }
        catch(Exception e){
            escribirCabecera(obj, ignore);
        }
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
        String atributos = "";
        Field[] camposObj= obj.getClass().getDeclaredFields();
        for(Field campo: camposObj){
            if(isNot(campo.getName(), ignore))
                atributos+="@@"+campo.getName()+":"+campo.getType().getName();
        }
        ArchivoUtil.guardarArchivo(ModelFactoryController.getRutaObjetos(obj), atributos.substring(2), true);
    }



    /**deserializa un usuario, devuelve un usuario a partir de los valores del archivo
     * @param usr objeto Usuario sobre el cual queremos quemar las propiedades
     * @param id id del usuario que buscamos
     * @return el mismo objeto usr, solo que con los atributos configurados
     * */
    public static Usuario deserializarUsuario(Usuario usr, Integer id) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        String[] atributos = {"Name", "Age", "Cedula", "Correo",
                              "CantAnuncios", "Direccion", "Password",
                              "IdListaPujas", "Activo", "Estado", "Id"};
        Class[] tipos = {String.class, Integer.class, String.class, String.class,
                         Integer.class, String.class, String.class,
                         Integer.class, Boolean.class, Estado.class, Integer.class};

        Class<?> c = Persistencia.class;
        ArrayList<Method> parse = new ArrayList<>();
        for(Class<?> clase: tipos){
            parse.add(c.getDeclaredMethod("parseTo"+clase.getSimpleName(), String.class));
        }

        ArrayList<String> listaUsuarios = ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos("Usuario.txt"));
        Class<?> claseUsr = usr.getClass();
        Method setter;
        for(String usuario: listaUsuarios) {
            String[] props = usuario.split("@@");
            if(props[10].equals(id+"")) {
                for (int i = 0; i < atributos.length; i++) {
                    setter = claseUsr.getDeclaredMethod("set" + atributos[i], tipos[i]);

                    setter.invoke(usr, parse.get(i).invoke(Persistencia.class, props[i]));
                }
                break;
            }
        }
        return usr;
    }

    //public static void serializarPuja(){}




    /*public static Usuario deserializarUsuario(String id) throws IOException, LecturaException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        ArrayList<String> usuarios = ArchivoUtil.leerArchivo(ModelFactoryController.getRutaObjetos("Usuario.txt"));
        Usuario usuario = new Usuario();
        String[] cabecera = usuarios.get(0).split("@@");
        String[] propiedades;
        for(String usr: usuarios){
            propiedades = usr.split("@@");
            if(propiedades[10].equals(id)){
                for(int i = 1; i<propiedades.length; i++){
                    String[] nombreTipo = cabecera[i].split(":");
                    Class<?> claseArgumento = Class.forName(nombreTipo[1]);
                    Method setter = usuario.getClass().getDeclaredMethod(nombreMetodo("set", nombreTipo[0]), claseArgumento);
                    setter.invoke(usuario, claseArgumento.cast(propiedades[i]));
                }
                return usuario;
            }
        }

        throw new LecturaException("usuario no encontrado", "intento fallido de deserializar usuario con id "+id);
    }

*/

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

}
