package software.ulpgc.aoc.day07;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public record TachyonSimulator(List<List<Cell>> grid, int accumulatedDivisions, int currentLayer) {

    // BFS
    public TachyonSimulator solve() {
        return IntStream.range(currentLayer, grid.size())
                .boxed()
                .reduce(
                        this,
                        (simulator, i) -> simulator.simulateNextLayer(),
                        (a, b) -> b
                );
    }

    private TachyonSimulator simulateNextLayer() {
        int newDivisions = 0;
        List<Cell> previousLayer = grid.get(currentLayer - 1);
        List<Cell> currentLayerList = grid.get(currentLayer);
        ArrayList<Cell> updatedLayer = new ArrayList<>();
        for (int i = 0; i < currentLayerList.size(); i++) {
            Cell currentCell = currentLayerList.get(i);
            Cell cellAbove = previousLayer.get(i);
            if (!cellAbove.isBeam()) {
                addOrReplace(updatedLayer, i, currentCell);
                continue;
            }
            if (!currentCell.isDivisor()) {
                propagateBeam(updatedLayer, i, cellAbove.intensity());
                continue;
            }
            propagateBeam(updatedLayer, i - 1, cellAbove.intensity());
            updatedLayer.add(Cell.divisor());
            propagateBeam(updatedLayer, i + 1, cellAbove.intensity());
            newDivisions++;
        }
        List<List<Cell>> newGrid = new ArrayList<>(grid);
        newGrid.set(currentLayer, updatedLayer);
        return new TachyonSimulator(newGrid, accumulatedDivisions + newDivisions, currentLayer + 1);
    }

    private void addOrReplace(List<Cell> layer, int index, Cell cell) {
        if (layer.size() <= index) {
            layer.add(cell);
        } else if (!layer.get(index).isBeam() || cell.isBeam()) {
            layer.set(index, cell);
        }
    }

    private void propagateBeam(List<Cell> layer, int index, long incomingIntensity) {
        if (index < 0) return;

        long currentIntensity = 0;
        if (layer.size() > index && layer.get(index).isBeam()) {
            currentIntensity = layer.get(index).intensity();
        }
        addOrReplace(layer, index, Cell.beam(currentIntensity + incomingIntensity));
    }
}