package software.ulpgc.aoc.day08;

import java.util.List;

public class LightsController {
    private final List<Circuit> circuits;

    public LightsController(List<Circuit> circuits) {
        this.circuits = circuits;
    }


    public long execute(long connectionCount) {
        return new CircuitConnector(circuits).calculateSafetyFactor(connectionCount);
    }
    // part2
    public long executeMerge() {
        return new CircuitConnector(circuits).calculateMergeCost();
    }
}