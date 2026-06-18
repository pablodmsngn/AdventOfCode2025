package software.ulpgc.aoc.day06.b;


import software.ulpgc.aoc.day06.InputLoader;
import software.ulpgc.aoc.day06.OperationBuilder;
import software.ulpgc.aoc.day06.CompactorController;

public class Main06B {
    public static void main(String[] args) {

        OperationBuilder analyzer = new CephalopodAnalyzer();

        CompactorController controller = InputLoader.load("day06input", analyzer);

        long result = controller.execute();

        System.out.println("--- DAY 6 PART 2: CEPHALOPOD MATHEMATICS ---");
        System.out.println("Grand Total: " + result);
    }
}