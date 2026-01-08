package software.ulpgc.aoc.dia09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class CargadorEntrada {
    public static ControladorCine cargar(String fichero) {
        InputStream is = CargadorEntrada.class.getClassLoader().getResourceAsStream(fichero);
        if (is == null) throw new RuntimeException("Fichero no encontrado: " + fichero);
        return cargar(is);
    }

    public static ControladorCine cargar(InputStream entrada) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(entrada))) {
            List<Coordenada> baldosas = reader.lines()
                    .map(Coordenada::desde)
                    .toList();
            return new ControladorCine(baldosas);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}