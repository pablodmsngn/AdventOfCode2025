package software.ulpgc.aoc.day10.b;

import software.ulpgc.aoc.day10.InputLoader;
import software.ulpgc.aoc.day10.FactoryController;

public class Main10B {
    public static void main(String[] args) {

        FactoryController controller = InputLoader.load("day10input");

        System.out.println("--- DAY 10 PART 2: SHAKE REQUIREMENTS ---");
        long result = controller.executePart2();

        System.out.println("Total minimum presses: " + result);
    }
}