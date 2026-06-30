package software.ulpgc.aoc.day03.application;

import software.ulpgc.aoc.common.io.LineLoader;
import software.ulpgc.aoc.common.io.ResourceLineLoader;
import software.ulpgc.aoc.day03.model.BatteryBank;

import java.util.List;

public class InputLoader {
    public static List<BatteryBank> load(String file) {
        LineLoader loader = new ResourceLineLoader(file);
        return loader.loadLines().stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(BatteryBank::new)
                .toList();
    }
}
