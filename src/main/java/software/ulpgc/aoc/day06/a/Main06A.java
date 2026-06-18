package software.ulpgc.aoc.day06.a;


import software.ulpgc.aoc.day06.InputLoader;
import software.ulpgc.aoc.day06.OperationBuilder;
import software.ulpgc.aoc.day06.CompactorController;

public class Main06A {

    public static void main(String[] args) {
        OperationBuilder analyzer = new VerticalAnalyzer();
        CompactorController controller = InputLoader.load("day06input", analyzer);

        long result = controller.execute();

        System.out.println("--- RESULT DAY 6: GARBAGE COMPACTOR ---");
        System.out.println("Total sum of all columns: " + result);
    }
}