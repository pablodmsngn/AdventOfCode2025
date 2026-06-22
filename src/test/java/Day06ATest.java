import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day06.application.InputLoader;
import software.ulpgc.aoc.day06.control.CompactorController;
import software.ulpgc.aoc.day06.model.Operation;
import software.ulpgc.aoc.day06.model.Operator;
import software.ulpgc.aoc.day06.control.VerticalAnalyzer;

import java.util.List;



import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Day 6 Part A: Vertical Compactor")
public class Day06ATest {

    @Test
    public void test_operation() {
        Operation sum = new Operation(List.of(1L, 4L, 5L), Operator.ADD);
        Operation multi = new Operation(List.of(1L, 2L, 5L), Operator.MULTIPLY);
        assertEquals(10L, sum.calculate());
        assertEquals(10L, multi.calculate());
    }

    @Test
    public void test_constructor_operations() {
        long[] results = new VerticalAnalyzer()
                .addLine(" 45 64  387 23")
                .addLine("  6 98  215 314")
                .addLine("*   +   *   +")
                .build()
                .mapToLong(Operation::calculate)
                .toArray();
        assertArrayEquals(new long[] {270, 162, 83205, 337}, results);

    }

    @Test
    public void test_runner_with_file() {
        CompactorController controller = InputLoader.load("day06input", new VerticalAnalyzer());
        long result = controller.execute();
        assertEquals(4364617236318L, result);
    }
}