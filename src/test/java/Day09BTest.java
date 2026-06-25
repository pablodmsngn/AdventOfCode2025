
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day09.control.AreaSolverFactory;
import software.ulpgc.aoc.day09.model.AreaSolver;
import software.ulpgc.aoc.day09.model.Coordinate;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Day09BTest {

    private final String EXAMPLE_INPUT = """
        7,1
        11,1
        11,7
        9,7
        9,5
        2,5
        2,3
        7,3
        """;

    @Test
    @DisplayName("Example: Maximum allowed area is 24")
    public void test_example_part2() {
        AreaSolver solver = new AreaSolverFactory()
                .from(EXAMPLE_INPUT.lines().map(Coordinate::from).toList())
                .type(AreaSolverFactory.SolverType.B)
                .build();
        long result = solver.solve();
        assertEquals(24L, result);
    }
}
