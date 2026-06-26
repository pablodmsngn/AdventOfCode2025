
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day11.control.RouteSolverFactory;
import software.ulpgc.aoc.day11.model.RouteGraph;
import software.ulpgc.aoc.day11.model.RouteSolver;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Day11BTest {

    private final String EXAMPLE_INPUT = """
        svr: aaa bbb
        aaa: fft
        fft: ccc
        bbb: tty
        tty: ccc
        ccc: ddd eee
        ddd: hub
        hub: fff
        eee: dac
        dac: fff
        fff: ggg hhh
        ggg: out
        hhh: out
        """;

    @Test
    public void testExamplePart2() {
        RouteSolver solver = new RouteSolverFactory()
                .from(RouteGraph.from(EXAMPLE_INPUT.lines().toList()))
                .type(RouteSolverFactory.SolverType.B)
                .build();
        long result = solver.solve();
        assertEquals(2L, result);
    }
}
