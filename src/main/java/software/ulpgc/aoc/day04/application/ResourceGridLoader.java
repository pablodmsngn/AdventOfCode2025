package software.ulpgc.aoc.day04.application;

import software.ulpgc.aoc.day04.io.GridLoader;
import software.ulpgc.aoc.day04.model.WarehouseGrid;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ResourceGridLoader implements GridLoader {
    private final String resource;

    public ResourceGridLoader(String resource) {
        this.resource = resource;
    }

    @Override
    public WarehouseGrid load() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(resource);
        if (stream == null) throw new RuntimeException("File not found: " + resource);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            List<String> lines = reader.lines().toList();
            return WarehouseGrid.from(lines);
        } catch (Exception e) {
            throw new RuntimeException("Error reading input", e);
        }
    }
}
