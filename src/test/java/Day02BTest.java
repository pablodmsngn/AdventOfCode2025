import software.ulpgc.aoc.day02.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;


public class Day02BTest {

    @Test
    @DisplayName("Strategy B must detect patterns repeated 2 times (Backward compatibility)")
    public void testStrategyBBase() {
        assertFalse(ValidationStrategies.PATTERN_B.test(123123), "123123 must be invalid in Phase 2");
        assertFalse(ValidationStrategies.PATTERN_B.test(11), "11 must be invalid in Phase 2");
    }

    @Test
    @DisplayName("Strategy B must detect patterns repeated 3 or more times")
    public void testStrategyBAdvanced() {
        assertFalse(ValidationStrategies.PATTERN_B.test(123123123), "123123123 (3 times) must be invalid in Phase 2");
        assertFalse(ValidationStrategies.PATTERN_B.test(111), "111 (3 times) must be invalid in Phase 2");
        assertFalse(ValidationStrategies.PATTERN_B.test(11111), "11111 (5 times) must be invalid in Phase 2");
    }

    @Test
    @DisplayName("Example from the statement Phase 2: Range 95-115")
    public void testStatementExample() {
        IdRange range = new IdRange(95, 115);
        long[] invalidItems = range.getInvalidIds(ValidationStrategies.PATTERN_B).toArray();
        assertArrayEquals(new long[]{99, 111}, invalidItems);
    }

    @Test
    @DisplayName("Engine must sum correctly with the new strategy")
    public void testEnginePhase2() {
        Engine engine = new Engine(Stream.of(new IdRange(95, 115)), ValidationStrategies.PATTERN_B);
        assertEquals(210, engine.run());
    }
}