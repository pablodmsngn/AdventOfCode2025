package software.ulpgc.aoc.day03.application;

import software.ulpgc.aoc.day03.io.SequenceLoader;
import software.ulpgc.aoc.day03.model.BatteryBank;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ResourceSequenceLoader implements SequenceLoader {
    private final String resource;

    public ResourceSequenceLoader(String resource) {
        this.resource = resource;
    }

    @Override
    public List<BatteryBank> loadAll() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(resource);
        if (in == null) throw new RuntimeException("File not found: " + resource);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            return reader.lines()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(BatteryBank::new)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error reading input", e);
        }
    }
}
