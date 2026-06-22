import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day05.control.InventoryAuditor;
import software.ulpgc.aoc.day05.model.Range;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class Day05BTest {

    @Test
    @DisplayName("Range: Detect overlaps between intervals")
    public void testRangeOverlap() {
        Range base = new Range(10, 20);

        assertAll("Interactions between ranges",
                () -> assertTrue(base.overlapsWith(new Range(5, 15)), "Overlaps left (5-15)"),
                () -> assertTrue(base.overlapsWith(new Range(15, 25)), "Overlaps right (15-25)"),
                () -> assertTrue(base.overlapsWith(new Range(12, 18)), "Internal overlap (12-18)"),
                () -> assertFalse(base.overlapsWith(new Range(1, 9)), "No overlap (is before)"),
                () -> assertFalse(base.overlapsWith(new Range(21, 30)), "No overlap (is after)")
        );
    }

    @Test
    @DisplayName("Range: Merge two overlapping ranges")
    public void testRangeMerge() {
        Range r1 = new Range(10, 15);
        Range r2 = new Range(12, 18);
        Range result = r1.merge(r2);
        assertEquals(10, result.min(), "The start must be the smaller of the two");
        assertEquals(18, result.max(), "The end must be the larger of the two");
        assertEquals(9, result.length(), "The total length must be 9");
    }

    @Test
    @DisplayName("Auditor: Simple coverage (Disjoint ranges)")
    public void testCoverageWithoutOverlap() {
        List<Range> ranges = new ArrayList<>();
        ranges.add(new Range(1, 2));
        ranges.add(new Range(4, 5));
        InventoryAuditor auditor = new InventoryAuditor(ranges, null, null);
        assertEquals(4, auditor.calculateTotalCoverage());
    }

    @Test
    @DisplayName("Auditor: Complex coverage (With merge)")
    public void testCoverageWithOverlap() {
        List<Range> ranges = new ArrayList<>();
        ranges.add(new Range(1, 5));
        ranges.add(new Range(4, 8));
        ranges.add(new Range(10, 10));
        InventoryAuditor auditor = new InventoryAuditor(ranges, null, null);
        assertEquals(9, auditor.calculateTotalCoverage(), "The algorithm must merge before summing");
    }
}