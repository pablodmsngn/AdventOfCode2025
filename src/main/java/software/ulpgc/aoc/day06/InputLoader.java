package software.ulpgc.aoc.day06;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class InputLoader {

    public static CompactorController load(String filename, OperationBuilder constructor) {
        InputStream inputStream = InputLoader.class.getClassLoader().getResourceAsStream(filename);

        if (inputStream == null) {
            throw new RuntimeException("File not found: " + filename);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            reader.lines().forEach(constructor::addLine);
            return new CompactorController(constructor.build());

        } catch (IOException e) {
            throw new RuntimeException("Error reading the file", e);
        }
    }
}
