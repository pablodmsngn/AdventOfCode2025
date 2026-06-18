import org.junit.jupiter.api.DisplayName;
import software.ulpgc.aoc.day02.*;
import org.junit.jupiter.api.Test;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;


public class Day02ATest {

    @Test
    @DisplayName("Strategy A must detect patterns repeated exactly 2 times")
    public void testStrategyA() {
        assertFalse(ValidationStrategies.PATTERN_A.test(123123), "123123 must be detected as invalid (false)");
        assertFalse(ValidationStrategies.PATTERN_A.test(11), "11 must be detected as invalid");
        assertFalse(ValidationStrategies.PATTERN_A.test(222222), "222222 (222-222) must be detected as invalid");
    }

    @Test
    @DisplayName("Strategy A must ignore patterns repeated 3 or more times")
    public void testStrategyAIgnoresMultiple() {
        assertTrue(ValidationStrategies.PATTERN_A.test(123123123), "123123123 should be valid in Phase 1");
        assertTrue(ValidationStrategies.PATTERN_A.test(111), "111 should be valid in Phase 1");
    }

    @Test
    @DisplayName("IdRange must correctly filter invalid IDs")
    public void testRangeId() {
        IdRange range = new IdRange(10, 12);
        long[] invalidItems = range.getInvalidIds(ValidationStrategies.PATTERN_A).toArray();
        assertArrayEquals(new long[]{11}, invalidItems);
    }

    @Test
    @DisplayName("Engine must correctly sum invalid IDs")
    public void testEngineIntegration() {
        IdRange range1 = new IdRange("10-12");
        IdRange range2 = new IdRange("20-22");
        Engine engine = new Engine(Stream.of(range1, range2), ValidationStrategies.PATTERN_A);
        assertEquals(33, engine.run());
    }
}