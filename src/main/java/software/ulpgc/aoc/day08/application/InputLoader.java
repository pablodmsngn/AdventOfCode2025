package software.ulpgc.aoc.day08.application;

import software.ulpgc.aoc.common.io.LineLoader;
import software.ulpgc.aoc.common.io.ResourceLineLoader;
import software.ulpgc.aoc.day08.control.SolverFactory;
import software.ulpgc.aoc.day08.model.Circuit;
import software.ulpgc.aoc.day08.model.CircuitSolver;

import java.util.List;

public class InputLoader {

    public static CircuitSolver loadSafetyFactor(String file, long connections) {
        return new SolverFactory()
                .from(parse(new ResourceLineLoader(file)))
                .type(SolverFactory.SolverType.A)
                .connections(connections)
                .build();
    }

    public static CircuitSolver loadMergeCost(String file) {
        return new SolverFactory()
                .from(parse(new ResourceLineLoader(file)))
                .type(SolverFactory.SolverType.B)
                .build();
    }

    private static List<Circuit> parse(LineLoader loader) {
        // Initially, each box is its own independent circuit
        return loader.loadLines().stream()
                .map(Circuit::fromText)
                .toList();
    }
}
