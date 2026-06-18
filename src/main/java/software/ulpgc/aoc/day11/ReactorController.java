package software.ulpgc.aoc.day11;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReactorController {
    private final Map<String, List<String>> graph;

    public ReactorController(Map<String, List<String>> graph) {
        this.graph = graph;
    }

    public long countTotalRoutes() {
        return new RouteAnalyzer(graph).countRoutes("you", "out");
    }

    public long countCriticalRoutes() {
        return new RouteAnalyzer(graph).countRoutesWithIntermediates("svr", "out", "dac", "fft");
    }
}