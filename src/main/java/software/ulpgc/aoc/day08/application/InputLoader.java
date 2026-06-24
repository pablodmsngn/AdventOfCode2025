package software.ulpgc.aoc.day08.application;

import software.ulpgc.aoc.day08.control.SolverFactory;
import software.ulpgc.aoc.day08.io.CircuitLoader;
import software.ulpgc.aoc.day08.model.Circuit;
import software.ulpgc.aoc.day08.model.CircuitSolver;

import java.util.List;

public class InputLoader {

    public static CircuitSolver loadSafetyFactor(String file, long connections) {
        return new SolverFactory()
                .from(parse(new ResourceCircuitLoader(file)))
                .type(SolverFactory.SolverType.A)
                .connections(connections)
                .build();
    }

    public static CircuitSolver loadMergeCost(String file) {
        return new SolverFactory()
                .from(parse(new ResourceCircuitLoader(file)))
                .type(SolverFactory.SolverType.B)
                .build();
    }

    private static List<Circuit> parse(CircuitLoader loader) {
        // Initially, each box is its own independent circuit
        return loader.loadLines().stream()
                .map(Circuit::fromText)
                .toList();
    }
}
