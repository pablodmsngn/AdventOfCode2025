package software.ulpgc.aoc.day04.application.b;

import software.ulpgc.aoc.day04.application.ResourceGridLoader;
import software.ulpgc.aoc.day04.control.ExecutorFactory;
import software.ulpgc.aoc.day04.io.GridLoader;
import software.ulpgc.aoc.day04.model.Executor;

public class Main04B {

    public static void main(String[] args) {

        GridLoader loader = new ResourceGridLoader("day04input");

        Executor solver = new ExecutorFactory()
                .from(loader.load())
                .type(ExecutorFactory.ExecutorType.B)
                .build();

        long result = solver.execute();

        System.out.println("--- DAY 4: PRINTING DEPARTMENT ---");
        System.out.println("Accessible rolls (Part B): " + result);
    }
}
