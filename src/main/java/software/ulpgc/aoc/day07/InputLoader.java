package software.ulpgc.aoc.day07;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputLoader {
    public static LabController load(String file) {
        InputStream is = InputLoader.class.getClassLoader().getResourceAsStream(file);
        if (is == null) throw new RuntimeException("File not found: " + file);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            GridBuilder builder = new GridBuilder();
            reader.lines().forEach(builder::addLine);
            return new LabController(builder.build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
