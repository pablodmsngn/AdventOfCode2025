package software.ulpgc.aoc.day09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class InputLoader {
    public static CinemaController load(String file) {
        InputStream is = InputLoader.class.getClassLoader().getResourceAsStream(file);
        if (is == null) throw new RuntimeException("File not found: " + file);
        return load(is);
    }

    public static CinemaController load(InputStream input) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            List<Coordinate> tiles = reader.lines()
                    .map(Coordinate::from)
                    .toList();
            return new CinemaController(tiles);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}