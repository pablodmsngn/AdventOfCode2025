package software.ulpgc.aoc.day06.application.b;

import software.ulpgc.aoc.day06.application.InputLoader;
import software.ulpgc.aoc.day06.control.CephalopodAnalyzer;
import software.ulpgc.aoc.day06.control.CompactorController;
import software.ulpgc.aoc.day06.model.OperationBuilder;

public class Main06B {

    public static void main(String[] args) {
        OperationBuilder analyzer = new CephalopodAnalyzer();
        CompactorController controller = InputLoader.load("day06input", analyzer);

        long result = controller.execute();

        System.out.println("--- DAY 6 PART 2: CEPHALOPOD MATHEMATICS ---");
        System.out.println("Grand Total: " + result);
    }
}
