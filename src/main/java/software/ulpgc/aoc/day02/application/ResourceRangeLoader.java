package software.ulpgc.aoc.day02.application;

import software.ulpgc.aoc.day02.io.RangeLoader;
import software.ulpgc.aoc.day02.model.IdRange;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Stream;


public class ResourceRangeLoader implements RangeLoader {
    private final String resourceName;

    public ResourceRangeLoader(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public Stream<IdRange> loadAll() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceName);
        if (inputStream == null) throw new RuntimeException("File not found: " + resourceName);

        return new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .flatMap(line -> Arrays.stream(line.split(",")))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(IdRange::new);
    }
}
