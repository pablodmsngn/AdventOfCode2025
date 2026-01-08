package software.ulpgc.aoc.dia04;

import java.util.stream.Stream;


public record Coordenada(int fila, int columna) {
    public Stream<Coordenada> vecinos() {
        return Stream.of(
                new Coordenada(fila-1, columna-1), new Coordenada(fila-1, columna), new Coordenada(fila-1, columna+1),
                new Coordenada(fila, columna-1),                                    new Coordenada(fila, columna+1),
                new Coordenada(fila+1, columna-1), new Coordenada(fila+1, columna), new Coordenada(fila+1, columna+1)
        );
    }
}