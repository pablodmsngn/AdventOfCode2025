package software.ulpgc.aoc.day07.application;

import software.ulpgc.aoc.day07.io.GridLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ResourceGridLoader implements GridLoader {
    private final String file;

    public ResourceGridLoader(String file) {
        this.file = file;
    }

    @Override
    public List<String> loadLines() {
        InputStream is = getClass().getClassLoader().getResourceAsStream(file);
        if (is == null) throw new RuntimeException("File not found: " + file);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
