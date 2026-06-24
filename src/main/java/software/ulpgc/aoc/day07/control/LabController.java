package software.ulpgc.aoc.day07.control;

import software.ulpgc.aoc.day07.model.Cell;
import software.ulpgc.aoc.day07.model.LabProtocol;
import software.ulpgc.aoc.day07.model.TachyonSimulator;

import java.util.List;

public record LabController(List<List<Cell>> grid, LabProtocol protocol) {

    public long run() {
        return protocol.measure(new TachyonSimulator(grid, 0, 1).solve());
    }
}
