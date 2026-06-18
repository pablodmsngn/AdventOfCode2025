package software.ulpgc.aoc.day08.b;


import software.ulpgc.aoc.day08.InputLoader;
import software.ulpgc.aoc.day08.LightsController;

public class Main08B {
    public static void main(String[] args) {

        LightsController controller = InputLoader.load("day08input");

        System.out.println("--- DAY 8 PART 2: CIRCUIT MERGING ---");
        long result = controller.executeMerge();

        System.out.println("Cost of the last connection (X1 * X2): " + result);
    }
}