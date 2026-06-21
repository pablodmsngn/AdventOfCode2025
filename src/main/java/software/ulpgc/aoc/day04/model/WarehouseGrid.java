package software.ulpgc.aoc.day04.model;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.ArrayList;

public record WarehouseGrid(List<List<CellContent>> grid) {

    public CellContent contentIn(Coordinate coord) {
        return grid.get(coord.row() + 1).get(coord.column() + 1);
    }

    private int width() {
        return grid.get(0).size() - 2;
    }

    private int height() {
        return grid.size() - 2;
    }

    // part2

    public WarehouseGrid removeRolls(List<Coordinate> toRemove) {
        List<List<CellContent>> newGrid = new ArrayList<>();
        for (List<CellContent> row : this.grid) {
            newGrid.add(new ArrayList<>(row));
        }
        for (Coordinate c : toRemove) {
            newGrid.get(c.row() + 1).set(c.column() + 1, CellContent.EMPTY);
        }
        return new WarehouseGrid(newGrid);
    }


    public static WarehouseGrid from(List<String> rawMap) {
        int rowWidth = rawMap.get(0).length();
        return new WarehouseGrid(addSecurityPadding(rawMap.stream(), rowWidth)
                .map(WarehouseGrid::fillRow)
                .map(WarehouseGrid::parseRow)
                .toList());
    }


    private static List<CellContent> parseRow(String row) {
        return row.chars()
                .mapToObj(c -> (char) c)
                .map(CellContent::fromChar)
                .toList();
    }

    private static Stream<String> addSecurityPadding(Stream<String> data, int width) {
        String emptyRow = ".".repeat(width);
        return Stream.concat(Stream.of(emptyRow), Stream.concat(data, Stream.of(emptyRow)));
    }

    private static String fillRow(String row) {
        return "." + row + ".";
    }

    public Stream<Coordinate> allCoordinates() {
        return IntStream.range(0, height())
                .mapToObj(row -> IntStream.range(0, width())
                        .mapToObj(col -> new Coordinate(row, col)))
                .flatMap(s -> s);
    }
}
