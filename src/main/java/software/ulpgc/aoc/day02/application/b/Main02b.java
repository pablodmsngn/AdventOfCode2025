package software.ulpgc.aoc.day02.application.b;

import software.ulpgc.aoc.day02.application.ResourceRangeLoader;
import software.ulpgc.aoc.day02.control.Engine;
import software.ulpgc.aoc.day02.control.EngineBuilder;
import software.ulpgc.aoc.day02.control.ValidationStrategies;
import software.ulpgc.aoc.day02.io.RangeLoader;


public class Main02b {
    public static void main(String[] args) {
        RangeLoader loader = new ResourceRangeLoader("day02input");

        Engine engine = new EngineBuilder()
                .from(loader.loadAll())
                .use(ValidationStrategies.PATTERN_B)
                .runner();

        System.out.println("--- RESULT DAY 2 (PART B) ---");
        System.out.println("Sum of invalid IDs: " + engine.run());
    }
}
