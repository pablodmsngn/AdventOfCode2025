package software.ulpgc.aoc.day11.control;

import software.ulpgc.aoc.day11.model.RouteGraph;
import software.ulpgc.aoc.day11.model.RouteSolver;

public class CriticalRoutesSolver implements RouteSolver {
    private final RouteGraph graph;

    public CriticalRoutesSolver(RouteGraph graph) {
        this.graph = graph;
    }

    @Override
    public long solve() {
        return new RouteAnalyzer(graph)
                .countRoutesWithIntermediates("svr", "out", "dac", "fft");
    }
}
