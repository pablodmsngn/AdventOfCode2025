package software.ulpgc.aoc.day11.b;


import software.ulpgc.aoc.day11.InputLoader;
import software.ulpgc.aoc.day11.ReactorController;

public class Main11B {
    public static void main(String[] args) {
        ReactorController controller = InputLoader.load("day11input");

        System.out.println("--- DAY 11 PART 2: CRITICAL ROUTES ---");
        long result = controller.countCriticalRoutes();

        System.out.println("Routes from 'svr' to 'out' passing through 'dac' and 'fft': " + result);
    }
}
