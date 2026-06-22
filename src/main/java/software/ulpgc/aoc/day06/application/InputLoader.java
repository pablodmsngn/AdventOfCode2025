package software.ulpgc.aoc.day06.application;

import software.ulpgc.aoc.day06.control.CompactorController;
import software.ulpgc.aoc.day06.io.LineLoader;
import software.ulpgc.aoc.day06.model.OperationBuilder;


public class InputLoader {

    public static CompactorController load(String filename, OperationBuilder builder) {
        LineLoader loader = new ResourceLineLoader(filename);
        loader.load().forEach(builder::addLine);
        return new CompactorController(builder.build());
    }
}
