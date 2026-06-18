package software.ulpgc.aoc.day04;

import java.io.InputStream;


public class PrintShopLoader {

    public static Executor load(String filename, ExecutorFactory.ExecutorType type) {
        InputStream stream = PrintShopLoader.class.getClassLoader().getResourceAsStream(filename);
        if (stream == null) {
            throw new RuntimeException("File not found: " + filename);
        }
        return new ExecutorFactory()
                .from(stream)
                .type(type)
                .build();
    }
}