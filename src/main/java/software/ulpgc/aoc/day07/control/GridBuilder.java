package software.ulpgc.aoc.day07.control;

import software.ulpgc.aoc.day07.model.Cell;
import software.ulpgc.aoc.day07.model.LabProtocol;

import java.util.List;

public class GridBuilder {
    private List<String> lines;
    private LabProtocol protocol;

    public GridBuilder from(List<String> lines) {
        this.lines = lines;
        return this;
    }

    public GridBuilder using(LabProtocol protocol) {
        this.protocol = protocol;
        return this;
    }

    public LabController build() {
        if (lines == null) throw new IllegalStateException("Missing input lines");
        if (protocol == null) throw new IllegalStateException("Missing lab protocol");
        List<List<Cell>> grid = lines.stream()
                .map(this::parseLine)
                .toList();
        return new LabController(grid, protocol);
    }

    private List<Cell> parseLine(String line) {
        return line.chars()
                .mapToObj(c -> Cell.fromChar((char) c))
                .toList();
    }
}
