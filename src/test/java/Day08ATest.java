
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day08.control.SolverFactory;
import software.ulpgc.aoc.day08.model.Circuit;
import software.ulpgc.aoc.day08.model.CircuitSolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08ATest {

    private final String EXAMPLE_INPUT = """
        162,817,812
        57,618,57
        906,360,560
        592,479,940
        352,342,300
        466,668,158
        542,29,236
        431,825,988
        739,650,466
        52,470,668
        216,146,977
        819,987,18
        117,168,530
        805,96,715
        346,949,466
        970,615,88
        941,993,340
        862,61,35
        984,92,344
        425,690,689
        """;

    @Test
    public void test_statement_example() {
        long connectionsToTest = 10;
        CircuitSolver solver = new SolverFactory()
                .from(EXAMPLE_INPUT.lines().map(Circuit::fromText).toList())
                .type(SolverFactory.SolverType.A)
                .connections(connectionsToTest)
                .build();
        long result = solver.solve();
        assertEquals(40L, result);
    }
}
