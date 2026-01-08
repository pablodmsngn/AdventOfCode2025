package software.ulpgc.aoc.dia08;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CargadorEntrada {

    public static ControladorLuces cargar(String fichero) {
        InputStream is = CargadorEntrada.class.getClassLoader().getResourceAsStream(fichero);
        if (is == null) throw new RuntimeException("Fichero no encontrado: " + fichero);
        return cargar(is);
    }

    public static ControladorLuces cargar(InputStream entrada) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(entrada))) {
            List<Circuito> circuitos = new ArrayList<>();
            reader.lines().forEach(linea -> circuitos.add(parsearLinea(linea)));
            return new ControladorLuces(circuitos);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Circuito parsearLinea(String linea) {
        // Formato: "162,817,812"
        int[] coords = Arrays.stream(linea.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
        // Inicialmente, cada caja es su propio circuito independiente
        return new Circuito(Set.of(new Caja(coords[0], coords[1], coords[2])));
    }
}