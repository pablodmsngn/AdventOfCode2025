package software.ulpgc.aoc.day07;


import java.util.List;

public class LabController {
    private final List<List<Cell>> grid;

    public LabController(List<List<Cell>> grid) {
        this.grid = grid;
    }



    public int countDivisions() {
        return new TachyonSimulator(grid, 0, 1)
                .solve()
                .accumulatedDivisions();
    }

    public long countTemporalLines() {
        var simulator = new TachyonSimulator(grid, 0, 1).solve();
        var lastLayer = simulator.grid().get(simulator.grid().size() - 1);
        return lastLayer.stream()
                .filter(Cell::isBeam)
                .mapToLong(Cell::intensity)
                .sum();
    }
}