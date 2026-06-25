package software.ulpgc.aoc.day10.model;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public record Button(Set<Integer> affectedIndices) {
    // factory method written like a constructor but it is not a constructor
    public static Button from(String str) {
        return new Button(Arrays.stream(str.substring(1, str.length() - 1).split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toSet()));
    }
}
