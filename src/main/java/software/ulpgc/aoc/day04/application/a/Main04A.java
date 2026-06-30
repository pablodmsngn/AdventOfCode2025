package software.ulpgc.aoc.day04.application.a;

import software.ulpgc.aoc.day04.application.InputLoader;
import software.ulpgc.aoc.day04.control.ExecutorFactory;
import software.ulpgc.aoc.day04.model.Executor;

public class Main04A {
    public static void main(String[] args) {
        Executor solver = new ExecutorFactory()
                .from(InputLoader.load("day04input"))
                .type(ExecutorFactory.ExecutorType.A)
                .build();

        long result = solver.execute();

        System.out.println("--- DAY 4: PRINTING DEPARTMENT ---");
        System.out.println("Accessible rolls (Part A): " + result);
    }
}
