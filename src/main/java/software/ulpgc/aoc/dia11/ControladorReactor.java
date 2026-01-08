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

    public long contarRutasCriticas() {
        return new AnalizadorRutas(grafo).contarRutasConIntermedios("svr", "out", "dac", "fft");
    }
}