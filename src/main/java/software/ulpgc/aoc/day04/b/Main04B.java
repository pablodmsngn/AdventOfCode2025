package software.ulpgc.aoc.day04.b;



import software.ulpgc.aoc.day04.PrintShopLoader;
import software.ulpgc.aoc.day04.Executor;
import software.ulpgc.aoc.day04.ExecutorFactory;

public class Main04B {

    public static void main(String[] args) {
        Executor solver = PrintShopLoader.load(
                "day04input",
                ExecutorFactory.ExecutorType.B
        );
        long result = solver.execute();
        System.out.println("--- DAY 4: PRINTING DEPARTMENT ---");
        System.out.println("Accessible rolls (Part B): " + result);
    }
}