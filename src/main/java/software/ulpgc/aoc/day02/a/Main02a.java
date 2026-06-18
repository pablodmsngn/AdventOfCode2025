package software.ulpgc.aoc.day02.a;

import software.ulpgc.aoc.day02.InputLoader;
import software.ulpgc.aoc.day02.ValidationStrategies;
import software.ulpgc.aoc.day02.Engine;
import java.util.function.LongPredicate;

public class Main02a {
    public static void main(String[] args) {
        LongPredicate strategy = ValidationStrategies.PATTERN_A;
        Engine engine = InputLoader.load("day02input", strategy);
        long result = engine.run();

        System.out.println("--- RESULT DAY 2 ---");
        System.out.println("Sum of invalid IDs: " + result);
    }
}