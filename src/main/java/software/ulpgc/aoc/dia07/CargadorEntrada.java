package software.ulpgc.aoc.dia07;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CargadorEntrada {
    public static ControladorLaboratorio cargar(String fichero) {
        InputStream is = CargadorEntrada.class.getClassLoader().getResourceAsStream(fichero);
        if (is == null) throw new RuntimeException("Fichero no encontrado: " + fichero);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            ConstructorRejilla builder = new ConstructorRejilla();
            reader.lines().forEach(builder::agregarLinea);
            return new ControladorLaboratorio(builder.construir());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
