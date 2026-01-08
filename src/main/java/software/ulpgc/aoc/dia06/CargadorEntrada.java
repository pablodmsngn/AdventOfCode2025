package software.ulpgc.aoc.dia06;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class CargadorEntrada {

    public static ControladorCompactador cargar(String nombreFichero, ConstructorOperaciones constructor) {
        InputStream inputStream = CargadorEntrada.class.getClassLoader().getResourceAsStream(nombreFichero);

        if (inputStream == null) {
            throw new RuntimeException("Fichero no encontrado: " + nombreFichero);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            reader.lines().forEach(constructor::agregarLinea);
            return new ControladorCompactador(constructor.construir());

        } catch (IOException e) {
            throw new RuntimeException("Error al leer el fichero", e);
        }
    }
}
