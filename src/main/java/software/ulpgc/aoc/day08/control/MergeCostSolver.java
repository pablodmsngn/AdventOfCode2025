package software.ulpgc.aoc.day08.control;

import software.ulpgc.aoc.day08.model.Circuit;
import software.ulpgc.aoc.day08.model.CircuitSolver;

import java.util.List;

public class MergeCostSolver implements CircuitSolver {
    private final List<Circuit> circuits;

    public MergeCostSolver(List<Circuit> circuits) {
        this.circuits = circuits;
    }

    @Override
    public long solve() {
        return new CircuitConnector(circuits).calculateMergeCost();
    }
}
