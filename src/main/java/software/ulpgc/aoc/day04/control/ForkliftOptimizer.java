package software.ulpgc.aoc.day04.control;

import software.ulpgc.aoc.day04.model.CellContent;
import software.ulpgc.aoc.day04.model.Coordinate;
import software.ulpgc.aoc.day04.model.WarehouseGrid;

import java.util.stream.Stream;


public class ForkliftOptimizer {

    //avoid instantiation
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
