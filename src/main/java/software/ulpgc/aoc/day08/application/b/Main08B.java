package software.ulpgc.aoc.day08.application.b;

import software.ulpgc.aoc.day08.application.InputLoader;
import software.ulpgc.aoc.day08.model.CircuitSolver;

public class Main08B {
    public static void main(String[] args) {
        CircuitSolver solver = InputLoader.loadMergeCost("day08input");

        System.out.println("--- DAY 8 PART 2: CIRCUIT MERGING ---");
        long result = solver.solve();

        System.out.println("Cost of the last connection (X1 * X2): " + result);
    }
}
