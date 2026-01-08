package software.ulpgc.aoc.dia07;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public record SimuladorTaquiones(List<List<Celda>> rejilla, int divisionesAcumuladas, int capaActual) {

    // BFS
    public SimuladorTaquiones resolver() {
        return IntStream.range(capaActual, rejilla.size())
                .boxed()
                .reduce(
                        this,
                        (simulador, i) -> simulador.simularSiguienteCapa(),
                        (a, b) -> b
                );
    }

    private SimuladorTaquiones simularSiguienteCapa() {
        int nuevasDivisiones = 0;
        List<Celda> capaAnterior = rejilla.get(capaActual - 1);
        List<Celda> capaActualLista = rejilla.get(capaActual);
        ArrayList<Celda> capaActualizada = new ArrayList<>();
        for (int i = 0; i < capaActualLista.size(); i++) {
            Celda celdaActual = capaActualLista.get(i);
            Celda celdaArriba = capaAnterior.get(i);
            if (!celdaArriba.esHaz()) {
                agregarOReemplazar(capaActualizada, i, celdaActual);
                continue;
            }
            if (!celdaActual.esDivisor()) {
                propagarHaz(capaActualizada, i, celdaArriba.intensidad());
                continue;
            }
            propagarHaz(capaActualizada, i - 1, celdaArriba.intensidad());
            capaActualizada.add(Celda.divisor());
            propagarHaz(capaActualizada, i + 1, celdaArriba.intensidad());
            nuevasDivisiones++;
        }
        List<List<Celda>> nuevaRejilla = new ArrayList<>(rejilla);
        nuevaRejilla.set(capaActual, capaActualizada);
        return new SimuladorTaquiones(nuevaRejilla, divisionesAcumuladas + nuevasDivisiones, capaActual + 1);
    }

    private void agregarOReemplazar(List<Celda> capa, int indice, Celda celda) {
        if (capa.size() <= indice) {
            capa.add(celda);
        } else if (!capa.get(indice).esHaz() || celda.esHaz()) {
            capa.set(indice, celda);
        }
    }

    private void propagarHaz(List<Celda> capa, int indice, long intensidadEntrante) {
        if (indice < 0) return;

        long intensidadActual = 0;
        if (capa.size() > indice && capa.get(indice).esHaz()) {
            intensidadActual = capa.get(indice).intensidad();
        }
        agregarOReemplazar(capa, indice, Celda.haz(intensidadActual + intensidadEntrante));
    }
}