package software.ulpgc.aoc.day03.application.a;

import software.ulpgc.aoc.day03.application.ResourceSequenceLoader;
import software.ulpgc.aoc.day03.control.SearchStrategies;
import software.ulpgc.aoc.day03.control.StaircaseBuilder;
import software.ulpgc.aoc.day03.control.StaircaseController;
import software.ulpgc.aoc.day03.io.SequenceLoader;
import software.ulpgc.aoc.day03.model.EnergyProtocol;

public class Main03A {
    private final Integer number = 2;
    public static void main(String[] args) {

        SequenceLoader loader = new ResourceSequenceLoader("day03input");
        EnergyProtocol planA = sequence -> SearchStrategies.Greedy(sequence, 2);

        StaircaseController staircase = new StaircaseBuilder()
                .from(loader.loadAll())
                .use(planA)
                .build();

        long result = staircase.activate();

        System.out.println("--- RESULT DAY 3 ---");
        System.out.println("Total Shake (Plan A - 2 digits): " + result);
    }
}
