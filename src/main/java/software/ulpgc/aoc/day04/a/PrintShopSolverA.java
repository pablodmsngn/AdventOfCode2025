package software.ulpgc.aoc.day04.a;

import software.ulpgc.aoc.day04.WarehouseGrid;
import software.ulpgc.aoc.day04.Executor;
import software.ulpgc.aoc.day04.ForkliftOptimizer;

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