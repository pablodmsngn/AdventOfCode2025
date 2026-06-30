package software.ulpgc.aoc.day03.application.b;

import software.ulpgc.aoc.day03.application.InputLoader;
import software.ulpgc.aoc.day03.control.SearchStrategies;
import software.ulpgc.aoc.day03.control.StaircaseBuilder;
import software.ulpgc.aoc.day03.control.StaircaseController;
import software.ulpgc.aoc.day03.model.EnergyProtocol;

public class Main03B {
    public static void main(String[] args) {
        EnergyProtocol planB = sequence -> SearchStrategies.Greedy(sequence, 12);

        StaircaseController staircase = new StaircaseBuilder()
                .from(InputLoader.load("day03input"))
                .use(planB)
                .build();

        long result = staircase.activate();

        System.out.println("--- RESULT DAY 3 ---");
        System.out.println("Total Shake (Plan B - 12 digits): " + result);
    }
}
