import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day11.control.RouteSolverFactory;
import software.ulpgc.aoc.day11.model.RouteGraph;
import software.ulpgc.aoc.day11.model.RouteSolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11ATest {

    private final String EXAMPLE_INPUT = """
        aaa: you hhh
        you: bbb ccc
        bbb: ddd eee
        ccc: ddd eee fff
        ddd: ggg
        eee: out
        fff: out
        ggg: out
        hhh: ccc fff iii
        iii: out
        """;

    @Test
    @DisplayName("Example Part 1: Verify 5 routes")
    public void testExample() {
        RouteSolver solver = new RouteSolverFactory()
                .from(RouteGraph.from(EXAMPLE_INPUT.lines().toList()))
                .type(RouteSolverFactory.SolverType.A)
                .build();
        long result = solver.solve();
        assertEquals(5L, result);
    }
}
