package software.ulpgc.aoc.day05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryAuditor {
    private final List<Range> ranges;
    private final List<Long> availableIds;
    private final FreshnessProtocol protocol;

    public InventoryAuditor(List<Range> ranges, List<Long> ids, FreshnessProtocol protocol) {
        this.ranges = ranges;
        this.availableIds = ids;
        this.protocol = protocol;
    }


    public long calculateTotalCoverage() {
        if (ranges.isEmpty()) return 0;
        List<Range> sorted = new ArrayList<>(this.ranges);
        Collections.sort(sorted);
        List<Range> mergedAll = new ArrayList<>();
        Range current = sorted.get(0);
        for (int i = 1; i < sorted.size(); i++) {
            Range next = sorted.get(i);
            if (current.overlapsWith(next)) {
                current = current.merge(next);
            } else {
                mergedAll.add(current);
                current = next;
            }
        }
        mergedAll.add(current);
        return mergedAll.stream()
                .mapToLong(Range::length)
                .sum();
    }

    public long audit() {
        return availableIds.stream()
                .filter(id -> protocol.isFresh(id, ranges))
                .count();
    }
}
