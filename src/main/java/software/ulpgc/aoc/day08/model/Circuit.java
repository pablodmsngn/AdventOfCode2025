package software.ulpgc.aoc.day08.model;

import java.util.Arrays;
import java.util.Set;

public record Circuit(Set<Box> boxes) {

    // Format: "162,817,812" -> a single-box circuit
    public static Circuit fromText(String line) {
        int[] coords = Arrays.stream(line.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
        return new Circuit(Set.of(new Box(coords[0], coords[1], coords[2])));
    }
}
