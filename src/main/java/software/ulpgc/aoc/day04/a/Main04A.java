package software.ulpgc.aoc.day04.a;


import software.ulpgc.aoc.day04.PrintShopLoader;
import software.ulpgc.aoc.day04.Executor;
import software.ulpgc.aoc.day04.ExecutorFactory;

public class Main04A {

    public static void main(String[] args) {

        Executor solver = PrintShopLoader.load(
                "day04input",
                ExecutorFactory.ExecutorType.A
        );

        long result = solver.execute();

        System.out.println("--- DAY 4: PRINTING DEPARTMENT ---");
        System.out.println("Accessible rolls (Part A): " + result);
    }
}