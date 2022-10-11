package persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

//CLASE ENCARGADA DE MANEJAR LOS LOGS
public class ArchivoUtilLog {

    //VARIABLE CON LA FECHA EN LA QUE SE CREA EL LOG
    static String fechaSistema = "";


    /**guarda un registro log en la ruta especificada
     *
     * */

    public static void guardarRegistroLog(String mensajeLog, int nivel, String accion, String rutaArchivo) {
        //SI NO EXISTE LA RUTA, LA CREA
        try {
            ArchivoUtil.crearRuta(rutaArchivo);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        String log = "";
        Logger LOGGER = Logger.getLogger(accion);
        FileHandler fileHandler = null;
        cargarFechaSistema();
        try {

            fileHandler = new FileHandler(rutaArchivo,true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            switch (nivel) {
                case 1:
                    LOGGER.log(Level.INFO,accion+","+mensajeLog+","+fechaSistema) ;
                    break;

                case 2:
                    LOGGER.log(Level.WARNING,accion+","+mensajeLog+","+fechaSistema) ;
                    break;

                case 3:
                    LOGGER.log(Level.SEVERE,accion+","+mensajeLog+","+fechaSistema) ;
                    break;

                default:
                    break;
            }

        } catch (SecurityException e) {

            LOGGER.log(Level.SEVERE,e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            LOGGER.log(Level.SEVERE,e.getMessage());
            e.printStackTrace();
        }
        finally {

            fileHandler.close();
        }

    }

    private static void cargarFechaSistema() {

        String diaN = "";
        String mesN = "";
        String anioN = "";

        Calendar cal1 = Calendar.getInstance();


        int  dia = cal1.get(Calendar.DATE);
        int mes = cal1.get(Calendar.MONTH)+1;
        int anio = cal1.get(Calendar.YEAR);
        int hora = cal1.get(Calendar.HOUR);
        int minuto = cal1.get(Calendar.MINUTE);


        if(dia < 10){
            diaN+="0"+dia;
        }
        else{
            diaN+=""+dia;
        }
        if(mes < 10){
            mesN+="0"+mes;
        }
        else{
            mesN+=""+mes;
        }

        //		fecha_Actual+= a�o+"-"+mesN+"-"+ diaN;
        //		fechaSistema = a�o+"-"+mesN+"-"+diaN+"-"+hora+"-"+minuto;
        fechaSistema = anio+"-"+mesN+"-"+diaN;
        //		horaFechaSistema = hora+"-"+minuto;
    }


}
