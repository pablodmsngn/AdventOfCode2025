package software.ulpgc.aoc.day04.control;

import software.ulpgc.aoc.day04.model.Executor;
import software.ulpgc.aoc.day04.model.WarehouseGrid;

/**
 * Builder + Factory.
 * Allows configuring step by step which solver we want (A or B) over an
 * already-built domain model (WarehouseGrid), keeping I/O out of the assembly.
 */
public class ExecutorFactory {
    private WarehouseGrid warehouse;
    private ExecutorType type;

    public ExecutorFactory from(WarehouseGrid warehouse) {
        this.warehouse = warehouse;
        return this;
    }

    public ExecutorFactory type(ExecutorType type) {
        this.type = type;
        return this;
    }

    public Executor build() {
        if (warehouse == null) throw new IllegalStateException("Missing warehouse (.from)");
        if (type == null) throw new IllegalStateException("Missing type (.type)");

        if (type == ExecutorType.A) return new PrintShopSolverA(warehouse);
        return new PrintShopSolverB(warehouse);
    }

    public enum ExecutorType { A, B }
}
