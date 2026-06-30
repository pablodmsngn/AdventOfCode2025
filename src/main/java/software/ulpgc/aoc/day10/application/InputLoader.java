package software.ulpgc.aoc.day10.application;

import software.ulpgc.aoc.common.io.LineLoader;
import software.ulpgc.aoc.common.io.ResourceLineLoader;
import software.ulpgc.aoc.day10.control.FactoryController;
import software.ulpgc.aoc.day10.model.Machine;
import software.ulpgc.aoc.day10.model.MachineSolver;

import java.util.List;

public class InputLoader {
    public static FactoryController load(String file, MachineSolver solver) {
        LineLoader loader = new ResourceLineLoader(file);
        List<Machine> machines = loader.loadLines().stream()
                .map(Machine::from)
                .toList();
        return new FactoryController(machines, solver);
    }
}
