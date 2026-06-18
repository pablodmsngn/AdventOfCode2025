package software.ulpgc.aoc.day02;

import java.io.InputStream;
import java.util.function.LongPredicate;


public class InputLoader {
    public static Engine load(String filename, LongPredicate validator) {
        InputStream inputStream = InputLoader.class.getClassLoader().getResourceAsStream(filename);
        if (inputStream == null) {
            throw new RuntimeException("File not found: " + filename);
        }
        return new EngineBuilder()
                .from(inputStream)
                .use(validator)
                .runner();
    }
}