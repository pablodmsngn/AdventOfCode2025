package software.ulpgc.aoc.day02.application.a;

import software.ulpgc.aoc.day02.application.InputLoader;
import software.ulpgc.aoc.day02.control.Engine;
import software.ulpgc.aoc.day02.control.EngineBuilder;
import software.ulpgc.aoc.day02.control.ValidationStrategies;

public class Main02a {
    public static void main(String[] args) {
        Engine engine = new EngineBuilder()
                .from(InputLoader.load("day02input"))
                .use(ValidationStrategies.PATTERN_A)
                .runner();

        System.out.println("--- RESULT DAY 2 (PART A) ---");
        System.out.println("Sum of invalid IDs: " + engine.run());
    }
}
