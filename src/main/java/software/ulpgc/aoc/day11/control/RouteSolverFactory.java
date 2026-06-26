package software.ulpgc.aoc.day11.control;

import software.ulpgc.aoc.day11.model.RouteGraph;
import software.ulpgc.aoc.day11.model.RouteSolver;

public class RouteSolverFactory {

    public enum SolverType { A, B }

    private RouteGraph graph;
    private SolverType type;

    public RouteSolverFactory from(RouteGraph graph) {
        this.graph = graph;
        return this;
    }

    public RouteSolverFactory type(SolverType type) {
        this.type = type;
        return this;
    }

    public RouteSolver build() {
        if (graph == null) throw new IllegalStateException("Missing graph");
        if (type == null) throw new IllegalStateException("Missing solver type");
        return switch (type) {
            case A -> new TotalRoutesSolver(graph);
            case B -> new CriticalRoutesSolver(graph);
        };
    }
}
