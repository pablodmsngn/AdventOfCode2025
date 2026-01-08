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

    public boolean esVertical() {
        return c1.x() == c2.x();
    }

    public long minX() { return Math.min(c1.x(), c2.x()); }
    public long maxX() { return Math.max(c1.x(), c2.x()); }
    public long minY() { return Math.min(c1.y(), c2.y()); }
    public long maxY() { return Math.max(c1.y(), c2.y()); }


}