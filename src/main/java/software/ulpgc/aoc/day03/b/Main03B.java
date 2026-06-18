package software.ulpgc.aoc.day03.b;

import software.ulpgc.aoc.day03.InputLoader;
import software.ulpgc.aoc.day03.StaircaseController;
import software.ulpgc.aoc.day03.SearchStrategies;
import software.ulpgc.aoc.day03.EnergyProtocol;

public class Main03B {
    public static void main(String[] args) {

        EnergyProtocol planB = sequence -> SearchStrategies.Greedy(sequence, 12);

        StaircaseController staircase = InputLoader.load("day03input", planB);

        long result = staircase.activate();

        System.out.println("--- RESULT DAY 3 ---");
        System.out.println("Total Shake (Plan B - 12 digits): " + result);
    }
}
