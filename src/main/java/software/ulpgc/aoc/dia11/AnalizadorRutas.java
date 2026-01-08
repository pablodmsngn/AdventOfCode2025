package software.ulpgc.aoc.dia11;

import java.util.*;

public class AnalizadorRutas {
    private final Map<String, List<String>> conexiones;

    public AnalizadorRutas(Map<String, List<String>> conexiones) {
        this.conexiones = conexiones;
    }

    public long contarRutas(String inicio, String fin) {
        Map<String, Long> memoria = new HashMap<>();
        return dfs(inicio, fin, memoria);
    }

    // Parte 2
    public long contarRutasConIntermedios(String inicio, String fin, String int1, String int2) {
        long rutasOrdenA = multiplicarCaminos(inicio, int1, int2, fin);
        long rutasOrdenB = multiplicarCaminos(inicio, int2, int1, fin);
        return rutasOrdenA + rutasOrdenB;
    }

    private long multiplicarCaminos(String n1, String n2, String n3, String n4) {
        long tramo1 = contarRutas(n1, n2);
        if (tramo1 == 0) return 0;
        long tramo2 = contarRutas(n2, n3);
        if (tramo2 == 0) return 0;
        long tramo3 = contarRutas(n3, n4);
        return tramo1 * tramo2 * tramo3;
    }

    private long dfs(String actual, String fin, Map<String, Long> memoria) {
        if (actual.equals(fin)) return 1L;
        if (memoria.containsKey(actual)) return memoria.get(actual);
        List<String> vecinos = conexiones.getOrDefault(actual, Collections.emptyList());
        long totalRutas = 0;
        for (String vecino : vecinos) {
            totalRutas += dfs(vecino, fin, memoria);
        }
        memoria.put(actual, totalRutas);
        return totalRutas;
    }
}