package software.ulpgc.aoc.day04;


import software.ulpgc.aoc.day04.a.PrintShopSolverA;
import software.ulpgc.aoc.day04.b.PrintShopSolverB;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Builder Pattern
 * Allows configuring step by step which solver we want (A or B) and with which file,
 * making the client code much more readable.
 */


public class ExecutorFactory {
    private InputStream file;
    private ExecutorType type;

    public ExecutorFactory from(InputStream file) {
        this.file = file;
        return this;
    }

    public ExecutorFactory type(ExecutorType type) {
        this.type = type;
        return this;
    }

    public Executor build() {
        if (file == null) throw new IllegalStateException("Missing input");

        List<String> lines = new BufferedReader(new InputStreamReader(file))
                .lines()
                .toList();

        // We create the domain model only once
        WarehouseGrid initialWarehouse = WarehouseGrid.from(lines);

        // We inject the MODEL, not the Stream
        if (type == ExecutorType.A) return new PrintShopSolverA(initialWarehouse);
        return new PrintShopSolverB(initialWarehouse);
    }

    public enum ExecutorType { A, B }
}