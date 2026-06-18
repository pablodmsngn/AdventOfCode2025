package software.ulpgc.aoc.day09;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record RectangleFinder(List<Coordinate> redTiles) {

    public Stream<Rectangle> generateRectangles() {
        return IntStream.range(0, redTiles.size() - 1)
                .mapToObj(i -> IntStream.range(i + 1, redTiles.size())
                        .mapToObj(j -> new Rectangle(redTiles.get(i), redTiles.get(j))))
                .flatMap(s -> s)
                .sorted(Comparator.comparingLong(Rectangle::area).reversed());
    }

    public Rectangle findLargest() {
        return generateRectangles()
                .findFirst()
                .orElse(new Rectangle(new Coordinate(0, 0), new Coordinate(0, 0)));
    }

    public Rectangle findLargestAllowed() {
        List<Rectangle> polygonEdges = getPolygonEdges();
        return generateRectangles()
                .filter(r -> isValidRectangle(r, polygonEdges))
                .findFirst()
                .orElse(new Rectangle(new Coordinate(0, 0), new Coordinate(0, 0)));
    }

    private boolean isValidRectangle(Rectangle r, List<Rectangle> edges) {
        for (Rectangle edge : edges) {
            if (shortest(edge, r)) return false;
        }
        double centerX = (r.c1().x() + r.c2().x()) / 2.0;
        double centerY = (r.c1().y() + r.c2().y()) / 2.0;
        return pointIsInside(centerX, centerY, edges);
    }

    private boolean shortest(Rectangle edge, Rectangle r) {
        if (edge.isVertical()) {
            long x = edge.c1().x();
            boolean xInside = x > r.minX() && x < r.maxX();
            boolean yOverlaps = Math.max(edge.minY(), r.minY()) < Math.min(edge.maxY(), r.maxY());
            return xInside && yOverlaps;
        } else {
            long y = edge.c1().y();
            boolean yInside = y > r.minY() && y < r.maxY();
            boolean xOverlaps = Math.max(edge.minX(), r.minX()) < Math.min(edge.maxX(), r.maxX());
            return yInside && xOverlaps;
        }
    }

    private boolean pointIsInside(double x, double y, List<Rectangle> edges) {
        int intersections = 0;
        for (Rectangle edge : edges) {
            if (edge.isVertical()) {
                if (edge.c1().x() < x) {
                    if (y >= edge.minY() && y <= edge.maxY()) {
                        intersections++;
                    }
                }
            }
        }
        return intersections % 2 != 0;
    }

    private List<Rectangle> getPolygonEdges() {
        List<Rectangle> edges = new ArrayList<>();
        int n = redTiles.size();
        for (int i = 0; i < n; i++) {
            Coordinate current = redTiles.get(i);
            Coordinate next = redTiles.get((i + 1) % n);
            edges.add(new Rectangle(current, next));
        }
        return edges;
    }
}