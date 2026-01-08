package software.ulpgc.aoc.dia12;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record Forma(int id, List<Coordenada> puntos) {

    public int area() {
        return puntos.size();
    }

    public List<Forma> generarVariaciones() {
        Set<List<Coordenada>> unicos = new HashSet<>();

        Forma actual = this;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                unicos.add(actual.normalizar());
                actual = actual.rotar();
            }
            actual = actual.voltear();
        }

        return unicos.stream()
                .map(pts -> new Forma(id, pts))
                .toList();
    }

    private Forma rotar() {
        return new Forma(id, puntos.stream().map(Coordenada::rotar).toList());
    }

    private Forma voltear() {
        return new Forma(id, puntos.stream().map(Coordenada::voltear).toList());
    }

    private List<Coordenada> normalizar() {
        int minR = puntos.stream().mapToInt(Coordenada::r).min().orElse(0);
        int minC = puntos.stream().mapToInt(Coordenada::c).min().orElse(0);

        return puntos.stream()
                .map(p -> new Coordenada(p.r() - minR, p.c() - minC))
                .sorted(Comparator.comparingInt(Coordenada::r).thenComparingInt(Coordenada::c))
                .toList();
    }
}