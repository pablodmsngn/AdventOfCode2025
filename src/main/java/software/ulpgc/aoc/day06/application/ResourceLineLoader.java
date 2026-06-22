package software.ulpgc.aoc.day06.application;

import software.ulpgc.aoc.day06.io.LineLoader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class ResourceLineLoader implements LineLoader {
    private final String resource;

    public ResourceLineLoader(String resource) {
        this.resource = resource;
    }

    @Override
    public Stream<String> load() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(resource);
        if (in == null) throw new RuntimeException("File not found: " + resource);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            return reader.lines().toList().stream();
        } catch (Exception e) {
            throw new RuntimeException("Error reading input", e);
        }
    }
}
