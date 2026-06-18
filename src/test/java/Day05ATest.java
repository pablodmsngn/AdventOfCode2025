import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day05.InventoryAuditor;
import software.ulpgc.aoc.day05.FreshnessProtocol;
import software.ulpgc.aoc.day05.Range;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class Day05ATest {



    @Test
    @DisplayName("Range: Check whether a number is inside")
    public void testRangeContains() {
        Range range = new Range(10, 20);
        assertAll("Limits and internal values",
                () -> assertTrue(range.contains(10), "Must include the lower limit"),
                () -> assertTrue(range.contains(20), "Must include the upper limit"),
                () -> assertTrue(range.contains(15), "Must include intermediate values"),
                () -> assertFalse(range.contains(9), "Must not include smaller values"),
                () -> assertFalse(range.contains(21), "Must not include larger values")
        );
    }


    @Test
    @DisplayName("Auditor: Count fresh ingredients using strategy")
    public void testAuditPart1() {

        List<Range> ranges = List.of(new Range(5, 10));
        List<Long> ids = List.of(1L, 5L, 8L, 11L);
        FreshnessProtocol protocol = (id, rangeList) ->
                rangeList.stream().anyMatch(r -> r.contains(id));
        InventoryAuditor auditor = new InventoryAuditor(ranges, ids, protocol);
        long result = auditor.audit();
        assertEquals(2, result, "Should find exactly 2 fresh ingredients (5 and 8)");
    }
}
