package software.ulpgc.aoc.day07.application.b;

import software.ulpgc.aoc.day07.application.InputLoader;
import software.ulpgc.aoc.day07.control.LabController;
import software.ulpgc.aoc.day07.model.Cell;
import software.ulpgc.aoc.day07.model.LabProtocol;

public class Main07B {
    public static void main(String[] args) {
        LabProtocol countTemporalLines = solved -> {
            var grid = solved.grid();
            var lastLayer = grid.get(grid.size() - 1);
            return lastLayer.stream()
                    .filter(Cell::isBeam)
                    .mapToLong(Cell::intensity)
                    .sum();
        };

        LabController laboratory = InputLoader.load("day07input", countTemporalLines);

        System.out.println("--- DAY 7 PART 2: TACHYONIC MULTIVERSE ---");
        long totalLines = laboratory.run();
        System.out.println("Total active timelines: " + totalLines);
    }
}
