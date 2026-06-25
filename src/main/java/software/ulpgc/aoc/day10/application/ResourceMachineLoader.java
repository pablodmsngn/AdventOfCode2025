package software.ulpgc.aoc.day10.application;

import software.ulpgc.aoc.day10.io.MachineLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ResourceMachineLoader implements MachineLoader {
    private final String file;

    public ResourceMachineLoader(String file) {
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
