package utilities;

import java.time.LocalDateTime;

public class test {

    public static void main(String[] args) {
        System.out.println(obtenerFechaActual());
    }

    private static String obtenerFechaActual() {
        return LocalDateTime.now().toString();
    }
}
