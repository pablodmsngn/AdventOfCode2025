package software.ulpgc.aoc.day04.control;

import software.ulpgc.aoc.day04.model.Executor;
import software.ulpgc.aoc.day04.model.WarehouseGrid;

public class PrintShopSolverA implements Executor {
    private final WarehouseGrid warehouse;

    public PrintShopSolverA(WarehouseGrid warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public long execute() {
        return ForkliftOptimizer.countAccessibleRolls(warehouse);
    }
}
