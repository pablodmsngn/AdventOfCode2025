package software.ulpgc.aoc.day11.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record RouteGraph(Map<String, List<String>> connections) {

    public List<String> neighborsOf(String node) {
        return connections.getOrDefault(node, Collections.emptyList());
    }

    public static RouteGraph from(List<String> lines) {
        Map<String, List<String>> graph = new HashMap<>();
        for (String line : lines) {
            String[] parts = line.split(" ");
            String origin = parts[0].substring(0, parts[0].length() - 1);
            List<String> destinations = new ArrayList<>();
            for (int i = 1; i < parts.length; i++) {
                destinations.add(parts[i]);
            }
            graph.put(origin, destinations);
        }
        return new RouteGraph(graph);
    }
}
