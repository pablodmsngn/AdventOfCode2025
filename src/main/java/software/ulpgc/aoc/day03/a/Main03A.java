package software.ulpgc.aoc.day03.a;

import software.ulpgc.aoc.day03.InputLoader;
import software.ulpgc.aoc.day03.StaircaseController;
import software.ulpgc.aoc.day03.SearchStrategies;
import software.ulpgc.aoc.day03.EnergyProtocol;

public class Main03A {

    public static void main(String[] args) {

        EnergyProtocol planA = sequence -> SearchStrategies.Greedy(sequence, 2);

        StaircaseController staircase = InputLoader.load("day03input", planA);

        long result = staircase.activate();

        System.out.println("--- RESULT DAY 3 ---");
        System.out.println("Total Shake (Plan A - 2 digits): " + result);
    }

}
