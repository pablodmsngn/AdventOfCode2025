import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day12.InputLoader;
import software.ulpgc.aoc.day12.FarmController;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

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
        var inputStream = new ByteArrayInputStream(EXAMPLE_INPUT.getBytes(StandardCharsets.UTF_8));
        FarmController controller = InputLoader.load(inputStream);
        long result = controller.countValidRegions();
        assertEquals(2L, result, "The number of valid regions should be 2");
    }
}