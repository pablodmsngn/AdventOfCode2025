package software.ulpgc.aoc.day04.control;

import software.ulpgc.aoc.day04.model.Coordinate;
import software.ulpgc.aoc.day04.model.Executor;
import software.ulpgc.aoc.day04.model.WarehouseGrid;

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
