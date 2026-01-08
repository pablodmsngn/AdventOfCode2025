package software.ulpgc.aoc.dia11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class CargadorEntrada {

    public static ControladorReactor cargar(String fichero) {
        InputStream is = CargadorEntrada.class.getClassLoader().getResourceAsStream(fichero);
        if (is == null) throw new RuntimeException("Fichero no encontrado: " + fichero);
        return cargar(is);
    }

    public static ControladorReactor cargar(InputStream entrada) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(entrada))) {
            Map<String, List<String>> grafo = new HashMap<>();
            reader.lines().forEach(linea -> parsearLinea(linea, grafo));
            return new ControladorReactor(grafo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void parsearLinea(String linea, Map<String, List<String>> grafo) {
        String[] partes = linea.split(" ");
        String origen = partes[0].substring(0, partes[0].length() - 1);
        List<String> destinos = new ArrayList<>();
        for (int i = 1; i < partes.length; i++) {
            destinos.add(partes[i]);
        }
        grafo.put(origen, destinos);
    }
}