package software.ulpgc.aoc.day08.application.a;

import software.ulpgc.aoc.day08.application.InputLoader;
import software.ulpgc.aoc.day08.model.CircuitSolver;

public class Main08A {
    public static void main(String[] args) {
        CircuitSolver solver = InputLoader.loadSafetyFactor("day08input", 1000);
        System.out.println("Result Day 8: " + solver.solve());
    }
}
