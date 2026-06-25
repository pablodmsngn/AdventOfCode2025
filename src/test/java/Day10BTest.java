import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day10.control.FactoryController;
import software.ulpgc.aoc.day10.model.Machine;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Day10BTest {

    private final String EXAMPLE_INPUT = """
        [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
        [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
        [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
        """;

    @Test
    @DisplayName("Verify example: Total presses (Voltages) is 33")
    public void testExamplePart2() {
        FactoryController controller = new FactoryController(
                EXAMPLE_INPUT.lines().map(Machine::from).toList(),
                machine -> machine.solveVoltageRequirements(new HashMap<>()));
        long result = controller.execute();
        assertEquals(33L, result, "The total presses for the voltages should be 33");
    }
}
