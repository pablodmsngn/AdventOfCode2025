package software.ulpgc.aoc.day10.application.a;

import software.ulpgc.aoc.day10.application.InputLoader;
import software.ulpgc.aoc.day10.control.FactoryController;
import software.ulpgc.aoc.day10.model.Machine;
import software.ulpgc.aoc.day10.model.MachineSolver;

public class Main10A {
    public static void main(String[] args) {
        MachineSolver minPresses = Machine::solveMinPresses;

        FactoryController controller = InputLoader.load("day10input", minPresses);
        System.out.println("--- DAY 10: FACTORY ---");
        System.out.println("Total minimum presses: " + controller.execute());
    }
}
