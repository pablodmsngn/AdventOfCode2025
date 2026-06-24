package software.ulpgc.aoc.day07.application.a;

import software.ulpgc.aoc.day07.application.InputLoader;
import software.ulpgc.aoc.day07.control.LabController;
import software.ulpgc.aoc.day07.model.LabProtocol;

public class Main07A {
    public static void main(String[] args) {
        LabProtocol countDivisions = solved -> solved.accumulatedDivisions();

        LabController laboratory = InputLoader.load("day07input", countDivisions);

        System.out.println("--- DAY 7: LABORATORIES ---");
        long result = laboratory.run();
        System.out.println("Total beam divisions: " + result);
    }
}
