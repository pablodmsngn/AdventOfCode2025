package software.ulpgc.aoc.day02.application.a;

import software.ulpgc.aoc.day02.application.ResourceRangeLoader;
import software.ulpgc.aoc.day02.control.Engine;
import software.ulpgc.aoc.day02.control.EngineBuilder;
import software.ulpgc.aoc.day02.control.ValidationStrategies;
import software.ulpgc.aoc.day02.io.RangeLoader;


public class Main02a {
    public static void main(String[] args) {
        RangeLoader loader = new ResourceRangeLoader("day02input");

        Engine engine = new EngineBuilder()
                .from(loader.loadAll())
                .use(ValidationStrategies.PATTERN_A)
                .runner();

        System.out.println("--- RESULT DAY 2 (PART A) ---");
        System.out.println("Sum of invalid IDs: " + engine.run());
    }
}
