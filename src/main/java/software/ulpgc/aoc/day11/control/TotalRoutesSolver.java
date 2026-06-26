package software.ulpgc.aoc.day11.control;

import software.ulpgc.aoc.day11.model.RouteGraph;
import software.ulpgc.aoc.day11.model.RouteSolver;

public class TotalRoutesSolver implements RouteSolver {
    private final RouteGraph graph;

    public TotalRoutesSolver(RouteGraph graph) {
        this.graph = graph;
    }

    @Override
    public long solve() {
        return new RouteAnalyzer(graph).countRoutes("you", "out");
    }
}
