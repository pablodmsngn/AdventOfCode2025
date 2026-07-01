package software.ulpgc.aoc.common.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


public class ResourceLineLoader implements LineLoader {
    private final String resource;

    public ResourceLineLoader(String resource) {
        this.resource = resource;
    }

    @Override
    public List<String> loadLines() {
        InputStream is = getClass().getClassLoader().getResourceAsStream(resource);
        if (is == null) throw new RuntimeException("File not found: " + resource);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
