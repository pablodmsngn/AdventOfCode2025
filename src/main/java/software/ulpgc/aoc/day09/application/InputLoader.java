package software.ulpgc.aoc.day09.application;

import software.ulpgc.aoc.common.io.LineLoader;
import software.ulpgc.aoc.common.io.ResourceLineLoader;
import software.ulpgc.aoc.day09.control.AreaSolverFactory;
import software.ulpgc.aoc.day09.model.AreaSolver;
import software.ulpgc.aoc.day09.model.Coordinate;

import java.util.List;

public class InputLoader {

    public static AreaSolver loadMaxArea(String file) {
        return new AreaSolverFactory()
                .from(parse(new ResourceLineLoader(file)))
                .type(AreaSolverFactory.SolverType.A)
                .build();
    }

    public static AreaSolver loadAllowedArea(String file) {
        return new AreaSolverFactory()
                .from(parse(new ResourceLineLoader(file)))
                .type(AreaSolverFactory.SolverType.B)
                .build();
    }

    private static List<Coordinate> parse(LineLoader loader) {
        return loader.loadLines().stream()
                .map(Coordinate::from)
                .toList();
    }
}
