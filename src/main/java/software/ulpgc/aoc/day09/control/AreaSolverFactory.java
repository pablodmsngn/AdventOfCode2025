package software.ulpgc.aoc.day09.control;

import software.ulpgc.aoc.day09.model.AreaSolver;
import software.ulpgc.aoc.day09.model.Coordinate;

import java.util.List;

public class AreaSolverFactory {

    public enum SolverType { A, B }

    private List<Coordinate> redTiles;
    private SolverType type;

    public AreaSolverFactory from(List<Coordinate> redTiles) {
        this.redTiles = redTiles;
        return this;
    }

    public AreaSolverFactory type(SolverType type) {
        this.type = type;
        return this;
    }

    public AreaSolver build() {
        if (redTiles == null) throw new IllegalStateException("Missing red tiles");
        if (type == null) throw new IllegalStateException("Missing solver type");
        return switch (type) {
            case A -> new MaxAreaSolver(redTiles);
            case B -> new AllowedAreaSolver(redTiles);
        };
    }
}
