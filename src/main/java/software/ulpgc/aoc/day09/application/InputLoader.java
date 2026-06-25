package software.ulpgc.aoc.day09.application;

import software.ulpgc.aoc.day09.control.AreaSolverFactory;
import software.ulpgc.aoc.day09.io.TileLoader;
import software.ulpgc.aoc.day09.model.AreaSolver;
import software.ulpgc.aoc.day09.model.Coordinate;

import java.util.List;

public class InputLoader {

    public static AreaSolver loadMaxArea(String file) {
        return new AreaSolverFactory()
                .from(parse(new ResourceTileLoader(file)))
                .type(AreaSolverFactory.SolverType.A)
                .build();
    }

    public static AreaSolver loadAllowedArea(String file) {
        return new AreaSolverFactory()
                .from(parse(new ResourceTileLoader(file)))
                .type(AreaSolverFactory.SolverType.B)
                .build();
    }

    private static List<Coordinate> parse(TileLoader loader) {
        return loader.loadLines().stream()
                .map(Coordinate::from)
                .toList();
    }
}
