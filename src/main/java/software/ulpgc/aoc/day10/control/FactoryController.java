package software.ulpgc.aoc.day10.control;

import software.ulpgc.aoc.day10.model.Machine;
import software.ulpgc.aoc.day10.model.MachineSolver;

import java.util.List;

public class FactoryController {
    private final List<Machine> machines;
    private final MachineSolver solver;

    public FactoryController(List<Machine> machines, MachineSolver solver) {
        this.machines = machines;
        this.solver = solver;
    }

    public long execute() {
        return machines.stream()
                .mapToInt(solver::solve)
                .sum();
    }
}
