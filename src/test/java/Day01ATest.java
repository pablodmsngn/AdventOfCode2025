import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day01.control.Safe;
import software.ulpgc.aoc.day01.control.SecurityProtocols;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Day01ATest {

    @Test
    public void testRobustness() {

        Safe box = new Safe(SecurityProtocols.PART_A);
        box.rotate(null);
        box.rotate("");
        box.rotate("   ");
        assertEquals(0, box.getZeroCount());
    }

    @Test
    public void testInitialState() {
        Safe box = new Safe(SecurityProtocols.PART_A);
        assertEquals(0, box.getZeroCount());
    }

    @Test
    public void testStopAtZero() {
        Safe box = new Safe(SecurityProtocols.PART_A);
        box.rotate("R50");
        assertEquals(1, box.getZeroCount());
    }

    @Test
    public void testZeroCrossingIgnored() {
        Safe box = new Safe(SecurityProtocols.PART_A);
        box.rotate("R60");
        assertEquals(0, box.getZeroCount());
    }

    @Test
    public void testAcceptancePartA() {
        Safe box = new Safe(SecurityProtocols.PART_A);
        String[] inputs = {
                "L68", "L30", "R48", "L5", "R60",
                "L55", "L1", "L99", "R14", "L82"
        };
        for (String input : inputs) box.rotate(input);
        assertEquals(3, box.getZeroCount());
    }
}