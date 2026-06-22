package software.ulpgc.aoc.day05.application;

import software.ulpgc.aoc.day05.io.AuditLoader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ResourceAuditLoader implements AuditLoader {
    private final String resource;

    public ResourceAuditLoader(String resource) {
        this.resource = resource;
    }

    @Override
    public List<String> loadLines() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(resource);
        if (in == null) throw new RuntimeException("File not found: " + resource);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            return reader.lines().toList();
        } catch (Exception e) {
            throw new RuntimeException("Error reading input", e);
        }
    }
}
