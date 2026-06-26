import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day12.application.InputLoader;
import software.ulpgc.aoc.day12.control.FarmController;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12ATest {

    private final String EXAMPLE_INPUT = """
        0:
        ###
        ##.

        1:
        ###
        ##.
        .##

        2:
        .##
        ###
        ##.

        3:
        ##.
        ###
        ##.

        4:
        ###
        #..
        ###

        5:
        ###
        .#.
        ###

        4x4: 0 0 0 0 2 0
        12x5: 1 0 1 0 2 2
        12x5: 1 0 1 0 3 2
        """;

    @Test
    public void testCompleteExample() {
        FarmController controller = InputLoader.fromLines(EXAMPLE_INPUT.lines().toList());
        long result = controller.countValidRegions();
        assertEquals(2L, result, "The number of valid regions should be 2");
    }
}
