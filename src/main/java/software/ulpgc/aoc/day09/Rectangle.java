package software.ulpgc.aoc.day09;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public record Rectangle(Coordinate c1, Coordinate c2) {

    public long width() {
        return Math.abs(c1.x() - c2.x()) + 1;
    }

    public long height() {
        return Math.abs(c1.y() - c2.y()) + 1;
    }

    public long area() {
        return width() * height();
    }

    public boolean isVertical() {
        return c1.x() == c2.x();
    }

    public long minX() { return Math.min(c1.x(), c2.x()); }
    public long maxX() { return Math.max(c1.x(), c2.x()); }
    public long minY() { return Math.min(c1.y(), c2.y()); }
    public long maxY() { return Math.max(c1.y(), c2.y()); }


}