
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day09.InputLoader;
import software.ulpgc.aoc.day09.CinemaController;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

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
        var inputStream = new ByteArrayInputStream(EXAMPLE_INPUT.getBytes(StandardCharsets.UTF_8));
        CinemaController controller = InputLoader.load(inputStream);
        long result = controller.getMaxAllowedArea();
        assertEquals(24L, result);
    }
}