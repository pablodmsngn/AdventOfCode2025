import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day07.control.GridBuilder;
import software.ulpgc.aoc.day07.control.LabController;
import software.ulpgc.aoc.day07.model.Cell;
import software.ulpgc.aoc.day07.model.LabProtocol;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Day07BTest {

    private final String EXAMPLE_INPUT = """
        .......S.......
        ...............
        .......^.......
        ...............
        ......^.^......
        ...............
        .....^.^.^.....
        ...............
        ....^.^...^....
        ...............
        ...^.^...^.^...
        ...............
        ..^...^.....^..
        ...............
        .^.^.^.^.^...^.
        ...............
        """;

    @Test
    public void testTemporalLinesExample() {
        LabProtocol countTemporalLines = solved -> {
            var grid = solved.grid();
            var lastLayer = grid.get(grid.size() - 1);
            return lastLayer.stream()
                    .filter(Cell::isBeam)
                    .mapToLong(Cell::intensity)
                    .sum();
        };
        LabController controller = new GridBuilder()
                .from(EXAMPLE_INPUT.lines().toList())
                .using(countTemporalLines)
                .build();
        long result = controller.run();
        assertEquals(40L, result, "The total number of timelines should be 40");
    }
}
