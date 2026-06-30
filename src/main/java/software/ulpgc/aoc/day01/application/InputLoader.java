package software.ulpgc.aoc.day01.application;

import software.ulpgc.aoc.common.io.LineLoader;
import software.ulpgc.aoc.common.io.ResourceLineLoader;
import software.ulpgc.aoc.day01.model.Instruction;

import java.util.List;
import java.util.Optional;

public class InputLoader {
    public static List<Instruction> load(String file) {
        LineLoader loader = new ResourceLineLoader(file);
        return loader.loadLines().stream()
                .map(Instruction::of)
                .flatMap(Optional::stream)
                .toList();
    }
}
