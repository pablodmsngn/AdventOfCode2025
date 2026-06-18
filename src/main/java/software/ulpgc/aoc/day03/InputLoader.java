package software.ulpgc.aoc.day03;

import java.io.InputStream;

public class InputLoader {

    public static StaircaseController load(String filename, EnergyProtocol protocol) {
        InputStream inputStream = InputLoader.class.getClassLoader().getResourceAsStream(filename);

        if (inputStream == null) {
            throw new RuntimeException("File not found: " + filename);
        }

        // DRY: We reuse the Builder
        return new StaircaseBuilder()
                .from(inputStream)
                .use(protocol)
                .build();
    }
}
