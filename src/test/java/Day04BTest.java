import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day04.model.WarehouseGrid;
import software.ulpgc.aoc.day04.model.Executor;
import software.ulpgc.aoc.day04.control.PrintShopSolverB;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Day04BTest {

    @Test
    @DisplayName("Statement case: Complete simulation (Expected result: 43)")
    void testCompleteSimulation() {
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


        Executor solver = new PrintShopSolverB(warehouse);

        long result = solver.execute();

        assertEquals(43, result);
    }

    @Test
    @DisplayName("Logical case: Progressive Unlock")
    void testProgressiveUnlock() {

        String input = """
                .@.
                @@@
                .@.
                """;

        WarehouseGrid warehouse = WarehouseGrid.from(input.lines().toList());


        Executor solver = new PrintShopSolverB(warehouse);

        assertEquals(5, solver.execute());
    }
}