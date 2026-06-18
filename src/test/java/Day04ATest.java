import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day04.WarehouseGrid;
import software.ulpgc.aoc.day04.Executor;
import software.ulpgc.aoc.day04.a.PrintShopSolverA;
import software.ulpgc.aoc.day04.b.PrintShopSolverB;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04ATest {

    @Test
    @DisplayName("Statement case: Must detect 13 accessible rolls")
    void testStatementExample() {
        String input = """
                ..@@.@@@@.
                @@@.@.@.@@
                @@@@@.@.@@
                @.@@@@..@.
                @@.@@@@.@@
                .@@@@@@@.@
                .@.@.@.@@@
                @.@@@.@@@@
                .@@@@@@@@.
                @.@.@@@.@.
                """;

        List<String> lines = input.lines().toList();
        WarehouseGrid warehouse = WarehouseGrid.from(lines);


        Executor solver = new PrintShopSolverA(warehouse);

        long result = solver.execute();
        assertEquals(13, result);
    }

    @Test
    @DisplayName("Edge case: Small fully blocked map")
    void testBlockedMap() {
        String input = """
                @@@
                @@@
                @@@
                """;
        WarehouseGrid warehouse = WarehouseGrid.from(input.lines().toList());
        Executor solver = new PrintShopSolverA(warehouse);

        assertEquals(4, solver.execute());
    }
}