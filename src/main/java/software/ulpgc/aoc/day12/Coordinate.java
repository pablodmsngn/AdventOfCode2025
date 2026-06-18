package software.ulpgc.aoc.day12;

public record Coordinate(int r, int c) {
    public Coordinate rotate() {
        return new Coordinate(c, -r);
    }

    public Coordinate flip() {
        return new Coordinate(r, -c);
    }

}