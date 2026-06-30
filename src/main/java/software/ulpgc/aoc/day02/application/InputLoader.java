package software.ulpgc.aoc.day02.application;

import software.ulpgc.aoc.common.io.LineLoader;
import software.ulpgc.aoc.common.io.ResourceLineLoader;
import software.ulpgc.aoc.day02.model.IdRange;

import java.util.Arrays;
import java.util.stream.Stream;

public class InputLoader {
    public static Stream<IdRange> load(String file) {
        LineLoader loader = new ResourceLineLoader(file);
        return loader.loadLines().stream()
                .flatMap(line -> Arrays.stream(line.split(",")))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(IdRange::new);
    }
}
