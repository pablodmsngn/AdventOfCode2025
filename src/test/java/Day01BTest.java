import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day01.Safe;
import software.ulpgc.aoc.day01.SecurityProtocols;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01BTest {

    @Test
    public void testMultipleTurns() {
        Safe box = new Safe(SecurityProtocols.PART_B);
        box.rotate("R1000");
        assertEquals(10, box.getZeroCount());
    }

    @Test
    public void testRightCross() {
        Safe box = new Safe(SecurityProtocols.PART_B);
        box.rotate("R40");
        assertEquals(0, box.getZeroCount());
        box.rotate("R20");
        assertEquals(1, box.getZeroCount());
    }

    @Test
    public void testLeftCross() {
        Safe box = new Safe(SecurityProtocols.PART_B);
        box.rotate("L40");
        assertEquals(0, box.getZeroCount());
        box.rotate("L20");
        assertEquals(1, box.getZeroCount());
    }

    @Test
    public void testExactLandingOnZero() {
        Safe box = new Safe(SecurityProtocols.PART_B);
        box.rotate("R50");
        assertEquals(1, box.getZeroCount());
    }

    @Test
    public void testAcceptancePartB() {
        Safe box = new Safe(SecurityProtocols.PART_B);
        String[] inputs = {
                "L68", "L30", "R48", "L5", "R60",
                "L55", "L1", "L99", "R14", "L82"
        };

        for (String input : inputs) box.rotate(input);
        assertEquals(6, box.getZeroCount());
    }
}