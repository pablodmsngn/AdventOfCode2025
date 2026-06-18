package software.ulpgc.aoc.day10;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class InputLoader {

    public static FactoryController load(String file) {
        InputStream is = InputLoader.class.getClassLoader().getResourceAsStream(file);
        if (is == null) throw new RuntimeException("File not found: " + file);
        return load(is);
    }

    public static FactoryController load(InputStream input) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            List<Machine> machines = reader.lines()
                    .map(Machine::from)
                    .toList();
            return new FactoryController(machines);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}