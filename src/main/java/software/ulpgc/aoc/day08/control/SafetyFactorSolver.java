package software.ulpgc.aoc.day08.control;

import software.ulpgc.aoc.day08.model.Circuit;
import software.ulpgc.aoc.day08.model.CircuitSolver;

import java.util.List;

public class SafetyFactorSolver implements CircuitSolver {
    private final List<Circuit> circuits;
    private final long connectionCount;

    public SafetyFactorSolver(List<Circuit> circuits, long connectionCount) {
        this.circuits = circuits;
        this.connectionCount = connectionCount;
    }

    @Override
    public long solve() {
        return new CircuitConnector(circuits).calculateSafetyFactor(connectionCount);
    }
}
