package software.ulpgc.aoc.day07.model;

//factory method: metodo estático que devuelve y crea el objeto
public record Cell(CellType type, long intensity) {

    public static Cell empty() {
        return new Cell(CellType.EMPTY, 0);
    }

    public static Cell divisor() {
        return new Cell(CellType.DIVISOR, 0);
    }

    public static Cell beam(long intensity) {
        return new Cell(CellType.BEAM, intensity);
    }


    public static Cell beam() {
        return new Cell(CellType.BEAM, 1);
    }

    public static Cell fromChar(char c) {
        if (c == '^') return divisor();
        if (c == 'S') return beam();
        return empty();
    }


    public boolean isDivisor() { return type == CellType.DIVISOR; }
    public boolean isBeam() { return type == CellType.BEAM; }
}
