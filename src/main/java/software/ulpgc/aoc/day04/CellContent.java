package software.ulpgc.aoc.day04;


public enum CellContent {
    EMPTY, PAPER_ROLL;

    public static CellContent fromChar(char symbol) {
        return symbol == '@' ? PAPER_ROLL : EMPTY;
    }

    @Override
    public String toString() {
        return this == PAPER_ROLL ? "@" : ".";
    }
}
