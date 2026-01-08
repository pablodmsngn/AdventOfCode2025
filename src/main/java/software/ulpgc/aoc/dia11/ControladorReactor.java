package software.ulpgc.aoc.dia11;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorReactor {
    private final Map<String, List<String>> grafo;

    public ControladorReactor(Map<String, List<String>> grafo) {
        this.grafo = grafo;
    }

    public long contarRutasTotales() {
        return new AnalizadorRutas(grafo).contarRutas("you", "out");
    }
    // Parte 2: Rutas críticas pasando por 'dac' y 'fft'
    public long contarRutasCriticas() {
        // En el enunciado original el inicio es 'svr' (el servidor), no 'you'
        return new AnalizadorRutas(grafo).contarRutasConIntermedios("svr", "out", "dac", "fft");
    }
}