package software.ulpgc.aoc.day03;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;


public class StaircaseBuilder {
    private InputStream file;
    private EnergyProtocol protocol;

    public StaircaseBuilder from(InputStream file) {
        this.file = file;
        return this;
    }

    public StaircaseBuilder use(EnergyProtocol protocol) {
        this.protocol = protocol;
        return this;
    }

    public StaircaseController build() {
        if (file == null) throw new IllegalStateException("Missing data source (.from)");
        if (protocol == null) throw new IllegalStateException("Missing protocol (.using)");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {
            List<BatteryBank> banks = reader.lines()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(BatteryBank::new)
                    .collect(Collectors.toList());

            return new StaircaseController(banks, protocol);
        } catch (Exception e) {
            throw new RuntimeException("Error reading input", e);
        }
    }



}
