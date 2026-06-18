package software.ulpgc.aoc.day10;


import java.util.List;

public enum State {
    ON, OFF;

    public static State fromChar(char c) {
        return c == '#' ? ON : OFF;
    }

    public static List<State> parse(String str) {
        return str.substring(1, str.length() - 1)
                .chars()
                .mapToObj(c -> fromChar((char) c))
                .toList();
    }
}