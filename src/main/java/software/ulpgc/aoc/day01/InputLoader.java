package software.ulpgc.aoc.day01;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class InputLoader {
    public static void process(String filename, Safe safe) {
        try {
            URL resource = InputLoader.class.getClassLoader().getResource(filename);
            if (resource == null) throw new RuntimeException("File not found: " + filename);

            Path path = Paths.get(resource.toURI());

            try (Stream<String> lines = Files.lines(path)) {
                lines.map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .forEach(safe::rotate);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading input", e);
        }
    }
}