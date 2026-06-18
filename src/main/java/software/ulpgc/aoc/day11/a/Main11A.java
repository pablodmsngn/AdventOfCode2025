package software.ulpgc.aoc.day11.a;

import software.ulpgc.aoc.day11.InputLoader;
import software.ulpgc.aoc.day11.ReactorController;

public class Main11A {
    public static void main(String[] args) {
        ReactorController controller = InputLoader.load("day11input");

        System.out.println("--- DAY 11: REACTOR ---");
        long result = controller.countTotalRoutes();
        System.out.println("Total routes from 'you' to 'out': " + result);
    }
}