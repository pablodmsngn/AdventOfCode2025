package software.ulpgc.aoc.day11.application;

import software.ulpgc.aoc.day11.control.RouteSolverFactory;
import software.ulpgc.aoc.day11.io.GraphLoader;
import software.ulpgc.aoc.day11.model.RouteGraph;
import software.ulpgc.aoc.day11.model.RouteSolver;

public class InputLoader {

    public static RouteSolver loadTotalRoutes(String file) {
        return build(file, RouteSolverFactory.SolverType.A);
    }

    public static RouteSolver loadCriticalRoutes(String file) {
        return build(file, RouteSolverFactory.SolverType.B);
    }

    private static RouteSolver build(String file, RouteSolverFactory.SolverType type) {
        GraphLoader loader = new ResourceGraphLoader(file);
        RouteGraph graph = RouteGraph.from(loader.loadLines());
        return new RouteSolverFactory()
                .from(graph)
                .type(type)
                .build();
    }
}
