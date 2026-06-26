package software.ulpgc.aoc.day11.application.b;

import software.ulpgc.aoc.day11.application.InputLoader;
import software.ulpgc.aoc.day11.model.RouteSolver;

public class Main11B {
    public static void main(String[] args) {
        RouteSolver controller = InputLoader.loadCriticalRoutes("day11input");

        System.out.println("--- DAY 11 PART 2: CRITICAL ROUTES ---");
        long result = controller.solve();

        System.out.println("Routes from 'svr' to 'out' passing through 'dac' and 'fft': " + result);
    }
}
