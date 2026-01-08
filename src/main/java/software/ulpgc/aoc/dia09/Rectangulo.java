package software.ulpgc.aoc.dia09;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public record Rectangulo(Coordenada c1, Coordenada c2) {

    public long ancho() {
        return Math.abs(c1.x() - c2.x()) + 1;
    }

    public long alto() {
        return Math.abs(c1.y() - c2.y()) + 1;
    }

    public long area() {
        return ancho() * alto();
    }

    public boolean esLinea() {
        return c1.x() == c2.x() || c1.y() == c2.y();
    }

    public boolean esVertical() {
        return c1.x() == c2.x();
    }

    public long minX() { return Math.min(c1.x(), c2.x()); }
    public long maxX() { return Math.max(c1.x(), c2.x()); }
    public long minY() { return Math.min(c1.y(), c2.y()); }
    public long maxY() { return Math.max(c1.y(), c2.y()); }

    public boolean contiene(Coordenada c) {
        return c.x() >= minX() && c.x() <= maxX() &&
                c.y() >= minY() && c.y() <= maxY();
    }

    public boolean solapa(Rectangulo otro) {
        return maxX() >= otro.minX() && otro.maxX() >= minX() &&
                maxY() >= otro.minY() && otro.maxY() >= minY();
    }

    public Stream<Coordenada> obtenerBordes() {
        // Genera todas las coordenadas del perímetro del rectángulo
        return Stream.concat(
                LongStream.rangeClosed(minX(), maxX())
                        .mapToObj(x -> Stream.of(
                                new Coordenada(x, minY()),
                                new Coordenada(x, maxY())
                        )).flatMap(s -> s),
                LongStream.rangeClosed(minY() + 1, maxY() - 1)
                        .mapToObj(y -> Stream.of(
                                new Coordenada(minX(), y),
                                new Coordenada(maxX(), y)
                        )).flatMap(s -> s)
        );
    }
}