package software.ulpgc.aoc.dia04;

import java.util.stream.Stream;

/**
 * Representa una posición inmutable (fila, columna) en el mapa del almacén.
 * Responsabilidad: Manejar la aritmética de posiciones y calcular adyacencias.
 */
public record Coordenada(int fila, int columna) {

    /**
     * Genera las 8 coordenadas que rodean a esta posición (arriba, abajo, lados y diagonales).
     * Se usa para verificar si un rollo está bloqueado por sus vecinos.
     * @return Un flujo (Stream) con las 8 coordenadas vecinas.
     */
    public Stream<Coordenada> vecinos() {
        return Stream.of(
                new Coordenada(fila-1, columna-1), new Coordenada(fila-1, columna), new Coordenada(fila-1, columna+1),
                new Coordenada(fila, columna-1),                                    new Coordenada(fila, columna+1),
                new Coordenada(fila+1, columna-1), new Coordenada(fila+1, columna), new Coordenada(fila+1, columna+1)
        );
    }
}