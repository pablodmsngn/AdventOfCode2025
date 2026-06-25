package software.ulpgc.aoc.day09.control;

import software.ulpgc.aoc.day09.model.AreaSolver;
import software.ulpgc.aoc.day09.model.Coordinate;

import java.util.List;

public class MaxAreaSolver implements AreaSolver {
    private final List<Coordinate> redTiles;

    public MaxAreaSolver(List<Coordinate> redTiles) {
        this.redTiles = redTiles;
    }

    @Override
    public long solve() {
        return new RectangleFinder(redTiles).findLargest().area();
    }
}
