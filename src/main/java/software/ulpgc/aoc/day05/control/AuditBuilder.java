package software.ulpgc.aoc.day05.control;

import software.ulpgc.aoc.day05.model.FreshnessProtocol;
import software.ulpgc.aoc.day05.model.Range;

import java.util.ArrayList;
import java.util.List;

public class AuditBuilder {
    private List<String> lines;
    private FreshnessProtocol protocol;

    public AuditBuilder from(List<String> lines) {
        this.lines = lines;
        return this;
    }

    public AuditBuilder using(FreshnessProtocol protocol) {
        this.protocol = protocol;
        return this;
    }

    public InventoryAuditor build() {
        if (lines == null) throw new IllegalStateException("Missing data source (.from)");

        List<Range> ranges = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        boolean readingRanges = true;
        for (String raw : lines) {
            String line = raw.trim();
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
