package software.ulpgc.aoc.day04;

import java.util.stream.Stream;


/**
 * REFACTORING:
 * Switched to a 'utility class' (static methods).
 * Reason: Avoid instantiating a new object on each iteration of the Part B loop.
 * It is now a pure function: it receives a state (warehouse) and returns a result.
 */
public class ForkliftOptimizer {

    // avoid instantiation
    private ForkliftOptimizer() {}

    public static long countAccessibleRolls(WarehouseGrid warehouse) {
        return getAccessibleLocations(warehouse).count();
    }

    public static Stream<Coordinate> getAccessibleLocations(WarehouseGrid warehouse) {
        return warehouse.allCoordinates()
                .filter(pos -> warehouse.contentIn(pos) == CellContent.PAPER_ROLL)
                .filter(pos -> !isBlocked(pos, warehouse));
    }

    private static boolean isBlocked(Coordinate pos, WarehouseGrid warehouse) {
        return pos.neighbors()
                .map(warehouse::contentIn)
                .filter(content -> content == CellContent.PAPER_ROLL)
                .count() >= 4;
    }
}