package software.ulpgc.aoc.day03.application.a;

import software.ulpgc.aoc.day03.application.InputLoader;
import software.ulpgc.aoc.day03.control.SearchStrategies;
import software.ulpgc.aoc.day03.control.StaircaseBuilder;
import software.ulpgc.aoc.day03.control.StaircaseController;
import software.ulpgc.aoc.day03.model.EnergyProtocol;

public class Main03A {
    public static void main(String[] args) {
        EnergyProtocol planA = sequence -> SearchStrategies.Greedy(sequence, 2);

        StaircaseController staircase = new StaircaseBuilder()
                .from(InputLoader.load("day03input"))
                .use(planA)
                .build();

        long result = staircase.activate();

        System.out.println("--- RESULT DAY 3 ---");
        System.out.println("Total Shake (Plan A - 2 digits): " + result);
    }
}
