package software.ulpgc.aoc.day04.application;

import software.ulpgc.aoc.common.io.LineLoader;
import software.ulpgc.aoc.common.io.ResourceLineLoader;
import software.ulpgc.aoc.day04.model.WarehouseGrid;

public class InputLoader {
    public static WarehouseGrid load(String file) {
        LineLoader loader = new ResourceLineLoader(file);
        return WarehouseGrid.from(loader.loadLines());
    }
}
