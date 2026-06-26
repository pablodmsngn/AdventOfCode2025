package software.ulpgc.aoc.day12.model;

import java.util.*;

public record Shape(int id, List<Coordinate> points) {

    public int area() {
        return points.size();
    }

    public List<Shape> generateVariations() {
        Set<List<Coordinate>> unique = new HashSet<>();
        Shape current = this;

        // We generate the 8 isometries
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                unique.add(current.normalize());
                current = current.rotate();
            }
            current = current.flip();
        }

        return unique.stream()
                .map(pts -> new Shape(id, pts))
                .toList();
    }

    private Shape rotate() {
        return new Shape(id, points.stream().map(Coordinate::rotate).toList());
    }

    private Shape flip() {
        return new Shape(id, points.stream().map(Coordinate::flip).toList());
    }

    private List<Coordinate> normalize() {
        int minR = points.stream().mapToInt(Coordinate::r).min().orElse(0);
        int minC = points.stream().mapToInt(Coordinate::c).min().orElse(0);

        return points.stream()
                .map(p -> new Coordinate(p.r() - minR, p.c() - minC))
                .sorted(Comparator.comparingInt(Coordinate::r).thenComparingInt(Coordinate::c))
                .toList();
    }
}
