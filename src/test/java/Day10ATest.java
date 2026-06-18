import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day10.InputLoader;
import software.ulpgc.aoc.day10.FactoryController;


import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Day10ATest {

    private final String EXAMPLE_INPUT = """
        [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
        [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
        [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
        """;

    @Test
    @DisplayName("Verify example: Total minimum presses (Lights) is 7")
    public void testExamplePart1() {
        var inputStream = new ByteArrayInputStream(EXAMPLE_INPUT.getBytes(StandardCharsets.UTF_8));
        FactoryController controller = InputLoader.load(inputStream);
        long result = controller.executePart1();
        assertEquals(7L, result, "The total presses to configure the lights should be 7");
    }
}