package software.ulpgc.aoc.day10.a;

import software.ulpgc.aoc.day10.InputLoader;
import software.ulpgc.aoc.day10.FactoryController;

public class Main10A {
    public static void main(String[] args) {
        FactoryController controller = InputLoader.load("day10input");
        System.out.println("--- DAY 10: FACTORY ---");
        System.out.println("Total minimum presses: " + controller.executePart1());
    }

    // The error message [0.491s][error][attach] failure (232)... is a JVM notice related to tools
    // for debugging or monitoring (like jcmd) trying to connect to the process. It is harmless and you can safely ignore it,
    // since it does not affect the program logic or its result.
}