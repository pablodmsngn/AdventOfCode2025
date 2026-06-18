package software.ulpgc.aoc.day08;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class InputLoader {

    public static LightsController load(String file) {
        InputStream is = InputLoader.class.getClassLoader().getResourceAsStream(file);
        if (is == null) throw new RuntimeException("File not found: " + file);
        return load(is);
    }

    public static LightsController load(InputStream input) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            List<Circuit> circuits = new ArrayList<>();
            reader.lines().forEach(line -> circuits.add(parseLine(line)));
            return new LightsController(circuits);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Circuit parseLine(String line) {
        // Format: "162,817,812"
        int[] coords = Arrays.stream(line.split(","))
                .map(String::trim) // removes the spaces
                .mapToInt(Integer::parseInt)
                .toArray();
        // Initially, each box is its own independent circuit
        return new Circuit(Set.of(new Box(coords[0], coords[1], coords[2])));
    }
}