package software.ulpgc.aoc.dia02;

import java.io.InputStream;
import java.util.function.LongPredicate;


public class CargadorEntrada {
    public static Motor cargar(String filename, LongPredicate validator) {
        // Obtenemos el InputStream
        InputStream inputStream = CargadorEntrada.class.getClassLoader().getResourceAsStream(filename);

        if (inputStream == null) {
            throw new RuntimeException("Archivo no encontrado: " + filename);
        }
        // REFACTORIZACIÓN: Usamos el ConstructorMotor para no repetir la lógica de parseo
        // dry explicar porque

        return new ConstructorMotor()
                .from(inputStream)
                .use(validator)
                .runner();
    }
}