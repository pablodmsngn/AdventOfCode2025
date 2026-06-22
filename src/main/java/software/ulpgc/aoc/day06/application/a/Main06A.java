package software.ulpgc.aoc.day06.application.a;

import software.ulpgc.aoc.day06.application.InputLoader;
import software.ulpgc.aoc.day06.control.CompactorController;
import software.ulpgc.aoc.day06.control.VerticalAnalyzer;
import software.ulpgc.aoc.day06.model.OperationBuilder;

public class Main06A {

    public static void main(String[] args) {
        OperationBuilder analyzer = new VerticalAnalyzer();
        CompactorController controller = InputLoader.load("day06input", analyzer);

        long result = controller.execute();

        System.out.println("--- RESULT DAY 6: GARBAGE COMPACTOR ---");
        System.out.println("Total sum of all columns: " + result);
    }
}
