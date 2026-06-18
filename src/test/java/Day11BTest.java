
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day11.InputLoader;
import software.ulpgc.aoc.day11.ReactorController;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

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
        var inputStream = new ByteArrayInputStream(EXAMPLE_INPUT.getBytes(StandardCharsets.UTF_8));
        ReactorController controller = InputLoader.load(inputStream);

        long result = controller.countCriticalRoutes();
        assertEquals(2L, result);
    }
}