

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day03.BatteryBank;
import software.ulpgc.aoc.day03.StaircaseController;
import software.ulpgc.aoc.day03.SearchStrategies;
import software.ulpgc.aoc.day03.EnergyProtocol;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Day03ATest {

    @Test
    @DisplayName("Statement case: Maximum at the start (98...)")
    public void testExample1() {
        String input = "987654321111111";
        long result = SearchStrategies.Greedy(input, 2);
        assertEquals(98L, result);
    }

    @Test
    @DisplayName("Statement case: Maximum at the ends (8...9)")
    public void testExample2() {
        String input = "811111111111119";
        long result = SearchStrategies.Greedy(input, 2);
        assertEquals(89L, result);
    }

    @Test
    @DisplayName("Statement case: Maximum at the end (...78)")
    public void testExample3() {
        String input = "234234234234278";
        long result = SearchStrategies.Greedy(input, 2);
        assertEquals(78L, result);
    }

    @Test
    @DisplayName("Statement case: Maximum interleaved (9...2)")
    public void testExample4() {
        String input = "818181911112111";
        long result = SearchStrategies.Greedy(input, 2);
        assertEquals(92L, result);
    }

    @Test
    @DisplayName("Integration: Total sum of a controller configured for 2 digits")
    public void testIntegrationPhase1() {
        List<BatteryBank> banks = List.of(
                new BatteryBank("987654321"), // gives 98
                new BatteryBank("111111191")  // gives 19 (1 at the start, 9 at the end)
        );

        EnergyProtocol planA = s -> SearchStrategies.Greedy(s, 2);
        StaircaseController controller = new StaircaseController(banks, planA);
        assertEquals(117L, controller.activate());
    }
}