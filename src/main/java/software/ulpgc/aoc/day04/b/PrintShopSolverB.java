package software.ulpgc.aoc.day04.b;

import software.ulpgc.aoc.day04.Coordinate;
import software.ulpgc.aoc.day04.WarehouseGrid;
import software.ulpgc.aoc.day04.Executor;
import software.ulpgc.aoc.day04.ForkliftOptimizer;

import java.util.List;

public class PrintShopSolverB implements Executor {

    private WarehouseGrid warehouse;

    public PrintShopSolverB(WarehouseGrid warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public long execute() {
        long totalRemoved = 0;
        boolean hasChanges = true;
        while (hasChanges) {
            List<Coordinate> accessibleRolls = ForkliftOptimizer
                    .getAccessibleLocations(warehouse)
                    .toList();
            if (accessibleRolls.isEmpty()) {
                hasChanges = false;
            } else {
                totalRemoved += accessibleRolls.size();
                warehouse = warehouse.removeRolls(accessibleRolls);
            }
        }
        return totalRemoved;
    }
}