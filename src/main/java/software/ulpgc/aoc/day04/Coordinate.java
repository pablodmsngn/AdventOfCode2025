package software.ulpgc.aoc.day04;

import java.util.stream.Stream;


public record Coordinate(int row, int column) {
    public Stream<Coordinate> neighbors() {
        return Stream.of(
                new Coordinate(row-1, column-1), new Coordinate(row-1, column), new Coordinate(row-1, column+1),
                new Coordinate(row, column-1),                                    new Coordinate(row, column+1),
                new Coordinate(row+1, column-1), new Coordinate(row+1, column), new Coordinate(row+1, column+1)
        );
    }
}