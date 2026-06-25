package software.ulpgc.aoc.day10.application.b;

import software.ulpgc.aoc.day10.application.InputLoader;
import software.ulpgc.aoc.day10.control.FactoryController;
import software.ulpgc.aoc.day10.model.Machine;
import software.ulpgc.aoc.day10.model.MachineSolver;

import java.util.HashMap;

public class Main10B {
    public static void main(String[] args) {
        MachineSolver voltageRequirements = machine -> machine.solveVoltageRequirements(new HashMap<>());

        FactoryController controller = InputLoader.load("day10input", voltageRequirements);

        System.out.println("--- DAY 10 PART 2: SHAKE REQUIREMENTS ---");
        long result = controller.execute();

        System.out.println("Total minimum presses: " + result);
    }
}
