import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day11.InputLoader;
import software.ulpgc.aoc.day11.ReactorController;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

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
        var inputStream = new ByteArrayInputStream(EXAMPLE_INPUT.getBytes(StandardCharsets.UTF_8));
        ReactorController controller = InputLoader.load(inputStream);
        long result = controller.countTotalRoutes();
        assertEquals(5L, result);
    }
}