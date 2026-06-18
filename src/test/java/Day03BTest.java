
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.ulpgc.aoc.day03.BatteryBank;
import software.ulpgc.aoc.day03.StaircaseController;
import software.ulpgc.aoc.day03.SearchStrategies;
import software.ulpgc.aoc.day03.EnergyProtocol;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Day03BTest {

    @Test
    @DisplayName("Validate large type support (Long Overflow check)")
    public void testLongSupport() {
        String input = "999999999999"; // 12 nines
        long expected = 999999999999L;
        long result = SearchStrategies.Greedy(input, 12);
        assertEquals(expected, result, "The algorithm must return a valid Long without overflowing");
    }

    @Test
    @DisplayName("Validate Greedy logic with 12 digits")
    public void testGreedyLogic12() {

        String input = "1999999999999";
        long expected = 999999999999L;
        long result = SearchStrategies.Greedy(input, 12);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Integration: Total sum of a controller configured for 12 digits")
    public void testIntegrationPhase2() {
        List<BatteryBank> banks = List.of(
                new BatteryBank("999999999999"),
                new BatteryBank("888888888888")
        );

        EnergyProtocol planB = s -> SearchStrategies.Greedy(s, 12);
        StaircaseController controller = new StaircaseController(banks, planB);
        long expected = 999999999999L + 888888888888L;
        assertEquals(expected, controller.activate());
    }
}