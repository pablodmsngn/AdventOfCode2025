package software.ulpgc.aoc.day10;

import java.util.HashMap;
import java.util.List;

public class FactoryController {
    private final List<Machine> machines;

    public FactoryController(List<Machine> machines) {
        this.machines = machines;
    }

    public long executePart1() {
        return machines.stream()
                .mapToInt(Machine::solveMinPresses)
                .sum();
    }

    public long executePart2() {
        return machines.stream()
                .mapToInt(m->m.solveVoltageRequirements(new HashMap<>()))
                .sum();
    }
}