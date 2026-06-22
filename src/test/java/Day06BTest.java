import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day06.application.InputLoader;
import software.ulpgc.aoc.day06.control.CompactorController;
import software.ulpgc.aoc.day06.model.Operation;
import software.ulpgc.aoc.day06.control.CephalopodAnalyzer;

import static org.junit.jupiter.api.Assertions.*;


public class Day06BTest {

    @Test
    public void test_operation_builder() {
        long[] results = new CephalopodAnalyzer()
                .addLine(" 45 64  387 23")
                .addLine("  6 98  215 314")
                .addLine("*   +   *   +")
                .build()
                .mapToLong(Operation::calculate)
                .toArray();
        assertArrayEquals(new long[] {224, 117, 194400, 58}, results);
    }

    @Test
    public void test_runner_with_inputs() {
        CompactorController controller = InputLoader.load("day06input", new CephalopodAnalyzer());
        long result = controller.execute();
        assertEquals(9077004354241L, result);
    }
}