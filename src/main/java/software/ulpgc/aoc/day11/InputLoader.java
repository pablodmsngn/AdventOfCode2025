package software.ulpgc.aoc.day11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class InputLoader {

    public static ReactorController load(String file) {
        InputStream is = InputLoader.class.getClassLoader().getResourceAsStream(file);
        if (is == null) throw new RuntimeException("File not found: " + file);
        return load(is);
    }

    public static ReactorController load(InputStream input) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            Map<String, List<String>> graph = new HashMap<>();
            reader.lines().forEach(line -> parseLine(line, graph));
            return new ReactorController(graph);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void parseLine(String line, Map<String, List<String>> graph) {
        String[] parts = line.split(" ");
        String origin = parts[0].substring(0, parts[0].length() - 1);
        List<String> destinations = new ArrayList<>();
        for (int i = 1; i < parts.length; i++) {
            destinations.add(parts[i]);
        }
        graph.put(origin, destinations);
    }
}