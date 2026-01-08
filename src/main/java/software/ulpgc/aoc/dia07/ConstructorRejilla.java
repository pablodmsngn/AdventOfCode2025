package software.ulpgc.aoc.dia07;

import java.util.ArrayList;
import java.util.List;

public class ConstructorRejilla {
    private final List<String> lineasRaw;

    public ConstructorRejilla() {
        this.lineasRaw = new ArrayList<>();
    }

    public ConstructorRejilla agregarLinea(String linea) {
        lineasRaw.add(linea);
        return this;
    }

    public List<List<Celda>> construir() {
        return lineasRaw.stream()
                .map(this::parsearLinea)
                .toList();
    }

    private List<Celda> parsearLinea(String linea) {
        return linea.chars()
                .mapToObj(c -> Celda.desdeCaracter((char) c))
                .toList();
    }
}