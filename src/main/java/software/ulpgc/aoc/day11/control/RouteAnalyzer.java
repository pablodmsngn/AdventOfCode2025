package software.ulpgc.aoc.day11.control;

import software.ulpgc.aoc.day11.model.RouteGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteAnalyzer {
    private final RouteGraph graph;

    public RouteAnalyzer(RouteGraph graph) {
        this.graph = graph;
    }

    public long countRoutes(String start, String end) {
        Map<String, Long> memory = new HashMap<>();
        return dfs(start, end, memory);
    }

    // Part 2
    public long countRoutesWithIntermediates(String start, String end, String int1, String int2) {
        long routesOrderA = multiplyPaths(start, int1, int2, end);
        long routesOrderB = multiplyPaths(start, int2, int1, end);
        return routesOrderA + routesOrderB;
    }

    private long multiplyPaths(String n1, String n2, String n3, String n4) {
        long segment1 = countRoutes(n1, n2);
        if (segment1 == 0) return 0;
        long segment2 = countRoutes(n2, n3);
        if (segment2 == 0) return 0;
        long segment3 = countRoutes(n3, n4);
        return segment1 * segment2 * segment3;
    }

    private long dfs(String current, String end, Map<String, Long> memory) {
        if (current.equals(end)) return 1L;
        if (memory.containsKey(current)) return memory.get(current);
        List<String> neighbors = graph.neighborsOf(current);
        long totalRoutes = 0;
        for (String neighbor : neighbors) {
            totalRoutes += dfs(neighbor, end, memory);
        }
        memory.put(current, totalRoutes);
        return totalRoutes;
    }
}
