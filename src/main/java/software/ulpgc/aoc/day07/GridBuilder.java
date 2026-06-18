package software.ulpgc.aoc.day07;

import java.util.ArrayList;
import java.util.List;

public class GridBuilder {
    private final List<String> rawLines;

    public GridBuilder() {
        this.rawLines = new ArrayList<>();
    }

    public GridBuilder addLine(String line) {
        rawLines.add(line);
        return this;
    }

    public List<List<Cell>> build() {
        return rawLines.stream()
                .map(this::parseLine)
                .toList();
    }

    private List<Cell> parseLine(String line) {
        return line.chars()
                .mapToObj(c -> Cell.fromChar((char) c))
                .toList();
    }
}