package utilities;

public class Utils {

    /**DEVUELVE TRUE SI LA CADENA SOURCE NO ES IGUAL A NINGUNA DE LAS CADENAS
     * EN LOS DEMAS ARGUMENTOS
     * @param source CADENA QUE SE COMPARA
     * @param targets CADENAS A LAS QUE PODRIA SER IGUAL SOURCE
     * @return SI LA CADENA SOURCE NO ES IGUAL A NINGUNA DE LAS CADENAS EN TARGET
     * */
    public static Boolean isNot(String source, String[] targets){
        for(String target: targets){
            if(source.equals(target)) return false;
        }
        return true;
    }
}
