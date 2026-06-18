package software.ulpgc.aoc.day05;


import java.io.InputStream;

public class InputLoader {

    public static InventoryAuditor load(String filename, FreshnessProtocol protocol) {
        InputStream inputStream = InputLoader.class.getClassLoader().getResourceAsStream(filename);

        if (inputStream == null) {
            throw new RuntimeException("File not found: " + filename);
        }
        return new AuditBuilder()
                .from(inputStream)
                .using(protocol)
                .build();
    }
}
