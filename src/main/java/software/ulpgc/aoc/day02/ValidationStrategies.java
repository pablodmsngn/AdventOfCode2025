package software.ulpgc.aoc.day02;

import java.util.function.LongPredicate;

public class ValidationStrategies {

    public static final LongPredicate PATTERN_A = id -> {
        return !Long.toString(id).matches("^(\\d+)\\1$");
    };

    public static final LongPredicate PATTERN_B = id -> {

        return !Long.toString(id).matches("^(\\d+)\\1+$");
    };
}
