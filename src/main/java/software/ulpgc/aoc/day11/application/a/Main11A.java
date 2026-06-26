package software.ulpgc.aoc.day11.application.a;

import software.ulpgc.aoc.day11.application.InputLoader;
import software.ulpgc.aoc.day11.model.RouteSolver;

public class Main11A {
    public static void main(String[] args) {
        RouteSolver controller = InputLoader.loadTotalRoutes("day11input");

        System.out.println("--- DAY 11: REACTOR ---");
        long result = controller.solve();
        System.out.println("Total routes from 'you' to 'out': " + result);
    }
}
