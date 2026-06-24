package software.ulpgc.aoc.day08.control;

import software.ulpgc.aoc.day08.model.Circuit;
import software.ulpgc.aoc.day08.model.CircuitSolver;

import java.util.List;

public class SolverFactory {

    public enum SolverType { A, B }

    private List<Circuit> circuits;
    private SolverType type;
    private long connections;

    public SolverFactory from(List<Circuit> circuits) {
        this.circuits = circuits;
        return this;
    }

    public SolverFactory type(SolverType type) {
        this.type = type;
        return this;
    }

    public SolverFactory connections(long connections) {
        this.connections = connections;
        return this;
    }

    public CircuitSolver build() {
        if (circuits == null) throw new IllegalStateException("Missing circuits");
        if (type == null) throw new IllegalStateException("Missing solver type");
        return switch (type) {
            case A -> new SafetyFactorSolver(circuits, connections);
            case B -> new MergeCostSolver(circuits);
        };
    }
}
