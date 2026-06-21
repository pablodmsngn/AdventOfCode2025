package software.ulpgc.aoc.day05;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuditBuilder {
    private List<String> lines;
    private FreshnessProtocol protocol;

    public AuditBuilder from(InputStream stream) {
        this.lines = new BufferedReader(new InputStreamReader(stream))
                .lines()
                .collect(Collectors.toList());
        return this;
    }

    public AuditBuilder using(FreshnessProtocol protocol) {
        this.protocol = protocol;
        return this;
    }

    public InventoryAuditor build() {
        List<Range> ranges = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        boolean readingRanges = true;
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) {
                readingRanges = false;
                continue;
            }
            if (readingRanges) {
                ranges.add(Range.fromText(line));
            } else {
                ids.add(Long.parseLong(line));
            }
        }
        return new InventoryAuditor(ranges, ids, protocol);
    }
}


